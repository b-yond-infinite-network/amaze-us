package com.ms.reminder.service.unit.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ms.reminder.model.Reminder;
import com.ms.reminder.repository.AppRepository;
import com.ms.reminder.service.AppService;



public class AppServiceTest {
	
	@Mock
	AppRepository apprepo;
	@InjectMocks
	AppService sut = new AppService();
	
	private static long COUNT_VAL=2L;
	
	@BeforeEach
	public void setUp() {
		
		MockitoAnnotations.openMocks(this);
		when(apprepo.count()).thenReturn(COUNT_VAL);
		
	}


    @Test
    public void countRemindersTest() {
    	
    	Integer count = sut.countReminders();
    	assertEquals(count.intValue(),2);
    }
    
    @Test
    public void findAllRemindersTest() {
    	Reminder reminderOne=Reminder.builder()
    			.id(123L)
    			.name("Reminder-1")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	Reminder reminderSecond=Reminder.builder()
    			.id(456L)
    			.name("Reminder-2")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	List<Reminder> reminders = new ArrayList<Reminder>();
    	reminders.add(reminderOne);
    	reminders.add(reminderSecond);
    	when(apprepo.findAll()).thenReturn(reminders);
    	List<Reminder> reminderList = sut.findAllReminders();
    	assertEquals(reminderList.size(),reminders.size());
    	verify(apprepo).findAll();
    }
    
    @Test
    public void findReminderTest() {
    	Reminder reminderOne=Reminder.builder()
    			.id(123L)
    			.name("Reminder-1")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	
    	when(apprepo.findById(123L)).thenReturn(Optional.of(reminderOne));
    	Reminder reminder = sut.findReminder(123L);
    	assertEquals(reminder.getName(),reminderOne.getName());
    }
    
    @Test
    public void createReminderTest() {
    	Reminder reminderTwo=Reminder.builder()
    			.id(456L)
    			.name("Reminder-2")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	
    	when(apprepo.save(reminderTwo)).thenReturn(reminderTwo);
    	
    	Reminder reminder = sut.createReminder(reminderTwo);
    	assertEquals(reminder.getId(),456L);
    }
    
    @Test
    public void editReminderTest() {
    	
    	Reminder reminderSaved=Reminder.builder()
    			.id(456L)
    			.name("Reminder-2")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	Reminder reminderTwo=Reminder.builder()
    			.id(456L)
    			.name("Reminder-2")
    			.isComplete(true)
    			.date(LocalDateTime.now())
    			.build();
    	
    	when(apprepo.findById(456L)).thenReturn(Optional.of(reminderSaved));
    	when(apprepo.save(reminderTwo)).thenReturn(reminderTwo);
    	
    	Reminder reminder = sut.editReminder(456L,reminderTwo);
    	assertEquals(reminder.getIsComplete(),true);
    }
    
    @Test
    public void deleteReminderTest() {
    	Reminder reminderSaved=Reminder.builder()
    			.id(456L)
    			.name("Reminder-2")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	
    	Long ID_VAL = 456L;
    	doNothing().when(apprepo).deleteById(ID_VAL);
    	when(apprepo.findById(ID_VAL)).thenReturn(Optional.of(reminderSaved));
    	
    	sut.deleteReminder(ID_VAL);
    	verify(apprepo).deleteById(ID_VAL);
    	
    }
	
    @Test
    public void findWithinDateRange() {
    	
    	Reminder reminderOne=Reminder.builder()
    			.id(123L)
    			.name("Reminder-1")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	Reminder reminderTwo=Reminder.builder()
    			.id(456L)
    			.name("Reminder-2")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	List<Reminder> reminders = new ArrayList<Reminder>();
    	reminders.add(reminderOne);
    	reminders.add(reminderTwo);
    	
    	when(apprepo.getRemindersWithinTimeRange(LocalDateTime.now().minusDays(5),
    			LocalDateTime.now().plusDays(5))).thenReturn(reminders);
    	
    	sut.findReminderInRange(LocalDateTime.now().minusDays(5), LocalDateTime.now().plusDays(5));
    	verify(apprepo).getRemindersWithinTimeRange(LocalDateTime.now().minusDays(5),
    			LocalDateTime.now().plusDays(5));
    	
    }
	
	@AfterEach
	public void tearDown() {
	     
	}
}
