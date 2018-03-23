package com.gly.zpower.baseadapter;
import java.util.Date;  

import com.gly.calendar.view.DateUtil;
  
public class DateRange {  
    private Date start;  
    private Date end;  
      
    public DateRange(Date start, Date end) {  
        this.start = start;  
        this.end = end;  
    }  
      
    public String getStart() {  
        return DateUtil.sf.format(start);  
    }  
    public void setStart(Date start) {  
        this.start = start;  
    }  
    public String getEnd() {  
        return DateUtil.sf.format(end);  
    }  
    public void setEnd(Date end) {  
        this.end = end;  
    }  
      
}  