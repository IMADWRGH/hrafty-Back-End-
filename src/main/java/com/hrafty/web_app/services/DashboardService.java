package com.hrafty.web_app.services;

import java.util.Map;

public interface DashboardService {

    Map<String, Object> getAdminStatistics();

    Map<String, Object> getSellerStatistics(Long sellerId);

    Map<String, Object> getCustomerStatistics(Long customerId);

    Map<String, Long> getOrderStatusDistribution();

    Map<String, Double> getRevenueByMonth(int year);

    Map<String, Long> getUserRegistrationByMonth(int year);
}