package me.lewei.log;
 
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
 
public class AppSystemCodePatternConverter extends PatternConverter {
 
    @Override
    protected String convert(LoggingEvent evt) {
    	LocationInfo info = evt.getLocationInformation();
    	String className = info.getClassName();

    	return className;
    }
} 