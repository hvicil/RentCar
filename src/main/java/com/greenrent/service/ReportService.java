package com.greenrent.service;

import com.greenrent.domain.Car;
import com.greenrent.domain.Reservation;
import com.greenrent.domain.User;
import com.greenrent.helper.ExcelReportHelper;
import com.greenrent.repository.CarRepository;
import com.greenrent.repository.ReservationRepository;
import com.greenrent.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    UserRepository userRepository;

    CarRepository carRepository;

    ReservationRepository reservationRepository;


    public ByteArrayInputStream getUserReport() throws IOException {
        List<User> users = userRepository.findAll();
        return ExcelReportHelper.getUsersExcelReport(users);

    }

    public ByteArrayInputStream getCarReport() throws IOException {
        List<Car> cars= carRepository.findAll();
        return ExcelReportHelper.getCarsExcelReport(cars);
    }

    public ByteArrayInputStream getReservationReport() throws IOException {
        List<Reservation> reservations= reservationRepository.findAll();
        return ExcelReportHelper.getReservationExcelReport(reservations);
    }






}
