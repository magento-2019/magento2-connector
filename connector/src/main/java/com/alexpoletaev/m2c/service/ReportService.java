package com.alexpoletaev.m2c.service;

import com.alexpoletaev.m2c.model.DateRequest;
import com.alexpoletaev.m2c.model.ReportResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReportService {
    @POST("rest/{storeCode}/V1/reports")
    Call<List<ReportResponse>> getAllReports(
        @Header("Content-Type") String contentType,
        @Header("Authorization") String accessToken,
        @Path("storeCode") String storeCode,
        @Body DateRequest dateRequest
    );
}
