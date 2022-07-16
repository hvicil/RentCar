package com.greenrent.controller;

import javax.validation.Valid;

import com.greenrent.dto.response.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.greenrent.dto.CarDTO;
import com.greenrent.dto.response.GRResponse;
import com.greenrent.service.CarService;
import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class CarController {

    private CarService carService;

    @PostMapping("/admin/{imageId}/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GRResponse> saveCar(@PathVariable String imageId,
                                              @Valid @RequestBody CarDTO carDTO){

        carService.saveCar(carDTO, imageId);

        GRResponse response=new GRResponse();
        response.setMessage(ResponseMessage.CAR_SAVED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/visitors/all")
    public ResponseEntity<List<CarDTO>> getAllCars(){
        List<CarDTO> carDTOs= carService.getAllCars();
        return ResponseEntity.ok(carDTOs);
    }

    @GetMapping("/visitors/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id){
        CarDTO carDTO= carService.findById(id);
        return ResponseEntity.ok(carDTO);
    }
    @GetMapping("/visitors/pages")
    public ResponseEntity<Page<CarDTO>> getAllWithPage(@RequestParam("page") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestParam("sort") String prop,
                                                       @RequestParam("direction") Sort.Direction direction){
        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        Page<CarDTO> carPage= carService.findAllWithPage(pageable);

        return ResponseEntity.ok(carPage);
    }

    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GRResponse> updateCar(@RequestParam("id") Long id,
                                                @RequestParam("imageId") String imageId,
                                                @Valid @RequestBody CarDTO carDTO){
        carService.updateCar(id,imageId,carDTO);
        GRResponse response=new GRResponse();
        response.setMessage(ResponseMessage.CAR_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/admin/{id}/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GRResponse> deleteCar(@PathVariable Long id){
        carService.removeById(id);

        GRResponse response=new GRResponse();
        response.setMessage(ResponseMessage.CAR_DELETE_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }








}