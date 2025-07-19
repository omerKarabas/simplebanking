package com.eteration.simplebanking.services;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CheckNumberService {
	
	private static final String CHECK_NUMBER_PREFIX = "CHK";
	private static final String CHECK_NUMBER_FORMAT = "%s-%s-%s-%04d";
	
	// Günlük sequence counter'ları
	private final Map<String, AtomicInteger> dailyCounters = new ConcurrentHashMap<>();
	
	/**
	 * Otomatik check number üretir
	 * Format: CHK-YYYY-MMDD-XXXX
	 * Örnek: CHK-2024-0115-0001, CHK-2024-0115-0002
	 */
	public String generateCheckNumber() {
		LocalDateTime now = LocalDateTime.now();
		String year = String.valueOf(now.getYear());
		String monthDay = String.format("%02d%02d", now.getMonthValue(), now.getDayOfMonth());
		
		// Günlük key oluştur
		String dailyKey = year + monthDay;
		
		// O gün için counter'ı al veya oluştur
		AtomicInteger counter = dailyCounters.computeIfAbsent(dailyKey, k -> new AtomicInteger(0));
		
		// Sequence numarasını al ve artır
		int sequence = counter.incrementAndGet();
		
		// Eğer sequence 9999'u geçerse, 1'e döndür
		if (sequence > 9999) {
			counter.set(1);
			sequence = 1;
		}
		
		return String.format(CHECK_NUMBER_FORMAT, CHECK_NUMBER_PREFIX, year, monthDay, sequence);
	}
	
	/**
	 * Belirli bir tarih için check number üretir (test için)
	 */
	public String generateCheckNumberForDate(LocalDateTime date) {
		String year = String.valueOf(date.getYear());
		String monthDay = String.format("%02d%02d", date.getMonthValue(), date.getDayOfMonth());
		
		String dailyKey = year + monthDay;
		AtomicInteger counter = dailyCounters.computeIfAbsent(dailyKey, k -> new AtomicInteger(0));
		int sequence = counter.incrementAndGet();
		
		if (sequence > 9999) {
			counter.set(1);
			sequence = 1;
		}
		
		return String.format(CHECK_NUMBER_FORMAT, CHECK_NUMBER_PREFIX, year, monthDay, sequence);
	}
	
	/**
	 * Günlük counter'ları temizler (her gün başında çağrılabilir)
	 */
	public void resetDailyCounters() {
		dailyCounters.clear();
	}
} 