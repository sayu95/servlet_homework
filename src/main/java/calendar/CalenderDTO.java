package calendar;

import java.io.Serializable;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalenderDTO implements Serializable {
	
	private Locale locale;
	private ZoneId zoneId;
	private YearMonth targetMonth;
	
	private int startDayOfWeek; 
	private int daysInMonth;   
    private List<String> dayNames;
    private String headerYearMonth;
	
	public CalenderDTO(Locale locale, ZoneId zoneId, YearMonth targetMonth) {
		this.locale = locale;
		this.zoneId = zoneId;
		this.targetMonth = targetMonth;
	}
    
    public int getYear() {
        return targetMonth != null ? targetMonth.getYear() : 0;
    }
    
    public int getMonthValue() {
        return targetMonth != null ? targetMonth.getMonthValue() : 0;
    }
}