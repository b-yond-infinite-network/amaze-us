package com.audela.challenge.busapi.validation;

import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.exception.DataValidationException;
import com.audela.challenge.busapi.exception.ScheduleConflictException;
import com.audela.challenge.busapi.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ScheduleValidation {

    @Autowired
    private ScheduleRepository scheduleRepository;

    private void checkMandatoryFields(ScheduleEntity schedule) throws DataValidationException{
        if(schedule.getStartStation() == null ||
                schedule.getDestinationStation() == null ||
                schedule.getEta() == null || schedule.getEtd() == null){
            throw new DataValidationException("Mandatory field missing for schedule");
        }else if(schedule.getBus() == null || schedule.getBus().getId() == null){
            throw new DataValidationException("Bus assignment missing");
        }else if(schedule.getDriver() == null || schedule.getDriver().getId() == null){
            throw new DataValidationException("Driver assignment missing");
        }
        if(!schedule.getEta().isAfter(schedule.getEtd())){
            throw new DataValidationException("Arrival time should be greater that Departure time");
        }
    }

    private void checkScheduleConflicts(ScheduleEntity schedule,
                                        List<ScheduleEntity> list,
                                        boolean isCreate,
                                        boolean isUpdate) throws ScheduleConflictException{
        boolean idDriverConflict = false;
        boolean idBusConflict = false;
        if(isCreate){
            idDriverConflict = list.stream().anyMatch(x->x.getDriver().getId() == schedule.getDriver().getId());
            idBusConflict = list.stream().anyMatch(x->x.getBus().getId() == schedule.getBus().getId());
        }else if(isUpdate){
            idDriverConflict = list.stream().filter(x->x.getId() != schedule.getId()).anyMatch(x->x.getDriver().getId() == schedule.getDriver().getId());
            idBusConflict = list.stream().filter(x->x.getId() != schedule.getId()).anyMatch(x->x.getBus().getId() == schedule.getBus().getId());
        }

        if(idDriverConflict && idBusConflict){
            throw new ScheduleConflictException("Driver schedule and Bus schedule conflicts");
        }else if(idDriverConflict){
            throw new ScheduleConflictException("Driver schedule conflicts");
        }else if(idBusConflict){
            throw new ScheduleConflictException("Bus schedule conflicts");
        }
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_UNCOMMITTED)
    public void validateForCreation(ScheduleEntity schedule) throws ScheduleConflictException, DataValidationException {
        checkMandatoryFields(schedule);
        List<ScheduleEntity> list = scheduleRepository.getScheduleWithinDateRange(schedule.getEtd(), schedule.getEta());
        checkScheduleConflicts(schedule, list, true, false);
    }

    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_UNCOMMITTED)
    public void validateForUpdate(ScheduleEntity schedule) throws ScheduleConflictException, DataValidationException {
        checkMandatoryFields(schedule);
        List<ScheduleEntity> list = scheduleRepository.getScheduleWithinDateRange(schedule.getEtd(), schedule.getEta());
        checkScheduleConflicts(schedule, list, false, true);
    }
}
