package com.greenrent.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.greenrent.exception.BadRequestException;
import com.greenrent.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.greenrent.domain.Car;
import com.greenrent.domain.ImageFile;
import com.greenrent.dto.CarDTO;
import com.greenrent.dto.mapper.CarMapper;
import com.greenrent.exception.ResourceNotFoundException;
import com.greenrent.exception.message.ErrorMessage;
import com.greenrent.repository.CarRepository;
import com.greenrent.repository.ImageFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CarService {
    private CarRepository carRepository;

    private CarMapper carMapper;

    private ImageFileRepository imageFileRepository;

    private ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public List<CarDTO> getAllCars(){
        List<Car> carList= carRepository.findAll();
        return carMapper.map(carList);
    }

    public CarDTO findById(Long id){
        Car car=carRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        return carMapper.carToCarDTO(car);
    }


    public void saveCar(CarDTO carDTO, String imageId) {
        ImageFile imFile=    imageFileRepository.findById(imageId).
                orElseThrow(()->new ResourceNotFoundException
                        (String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));

        Car car= carMapper.carDTOToCar(carDTO);

        Set<ImageFile> imFiles=new HashSet<>();
        imFiles.add(imFile);
        car.setImage(imFiles);

        carRepository.save(car);
    }

    @Transactional(readOnly=true)
    public Page<CarDTO> findAllWithPage(Pageable pageable){
        return carRepository.findAllCarWithPage(pageable);
    }

    /**
     * This method is used to update a car
     *
     * @param id Car id of the car that will be updated
     * @param imageId this is image id
     * @param carDTO This is carDTO to keep data about the car.
     */


    @Transactional
    public void updateCar(Long id, String imageId, CarDTO carDTO) {
        Car foundCar=carRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

        ImageFile imFile=imageFileRepository.findById(imageId).
                orElseThrow(()-> new ResourceNotFoundException (String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));

        if(foundCar.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        Set<ImageFile> imgs= foundCar.getImage();
        imgs.add(imFile);

        Car car = carMapper.carDTOToCar(carDTO);

        car.setId(foundCar.getId());
        car.setImage(imgs);

        carRepository.save(car);

    }

    public void removeById(Long id) {
        Car car=carRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException
                        (String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

        boolean exists=reservationRepository.existsByCarId(car);
        if (exists){
            throw new BadRequestException(ErrorMessage.CAR_USED_BY_RESERVATION_MESSAGE);
        }


        if(car.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        carRepository.deleteById(id);
    }



}