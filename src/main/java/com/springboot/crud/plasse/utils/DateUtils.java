package com.springboot.crud.plasse.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtils {
	
	public static boolean isMajeur(LocalDate birthDate) {
		return ChronoUnit.YEARS.between( birthDate , LocalDate.now()) > 18;
	}

}
