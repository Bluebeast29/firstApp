package com.example.firstapp.api.service;

import com.example.firstapp.api.ApiResponse;
import com.example.firstapp.api.dto.AttendanceDto;
import com.example.firstapp.api.dto.CourseDto;
import com.example.firstapp.api.dto.DepartmentDto;
import com.example.firstapp.api.dto.EnrollmentDto;
import com.example.firstapp.api.dto.FacultyDto;
import com.example.firstapp.api.dto.OfferingDto;
import com.example.firstapp.api.dto.SessionDto;
import com.example.firstapp.api.dto.StudentDto;
import com.example.firstapp.api.dto.StudentAttendanceSummaryDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {

    // Departments
    @GET("departments")
    Call<ApiResponse<List<DepartmentDto>>> getDepartments();

    @GET("departments/{id}")
    Call<ApiResponse<DepartmentDto>> getDepartment(@Path("id") String id);

    @GET("departments/{id}/faculty")
    Call<ApiResponse<List<FacultyDto>>> getDepartmentFaculty(@Path("id") String id);

    // Courses
    @GET("courses")
    Call<ApiResponse<List<CourseDto>>> getCourses();

    @GET("courses")
    Call<ApiResponse<List<CourseDto>>> getCourses(@Query("departmentId") String departmentId);

    @GET("courses/{id}")
    Call<ApiResponse<CourseDto>> getCourse(@Path("id") String id);

    @GET("courses/{id}/offerings")
    Call<ApiResponse<List<OfferingDto>>> getCourseOfferings(@Path("id") String id);

    // Students
    @GET("students")
    Call<ApiResponse<List<StudentDto>>> getStudents();

    @GET("students")
    Call<ApiResponse<List<StudentDto>>> getStudents(@Query("department") String departmentCode);

    @GET("auth/me")
    Call<ApiResponse<com.example.firstapp.api.dto.AuthResponse.UserData>> getMyStudentRecord();

    @GET("students/{id}")
    Call<ApiResponse<StudentDto>> getStudent(@Path("id") String id);

    @GET("students/{id}/enrollments")
    Call<ApiResponse<List<EnrollmentDto>>> getStudentEnrollments(@Path("id") String id);

    @GET("students/{id}/attendance")
    Call<ApiResponse<List<AttendanceDto>>> getStudentAttendance(@Path("id") String id);

    // Faculty
    @GET("faculty")
    Call<ApiResponse<List<FacultyDto>>> getFaculty();

    @GET("faculty/{id}")
    Call<ApiResponse<FacultyDto>> getFacultyById(@Path("id") String id);

    @GET("faculty/{id}/offerings")
    Call<ApiResponse<List<OfferingDto>>> getFacultyOfferings(@Path("id") String id);

    // Sessions
    @GET("sessions")
    Call<ApiResponse<List<SessionDto>>> getSessions();

    @GET("sessions/{id}")
    Call<ApiResponse<SessionDto>> getSession(@Path("id") String id);

    // Enrollments
    @GET("enrollments")
    Call<ApiResponse<List<EnrollmentDto>>> getEnrollments();

    // Offerings
    @GET("offerings")
    Call<ApiResponse<List<OfferingDto>>> getOfferings();

    @GET("offerings/{id}")
    Call<ApiResponse<OfferingDto>> getOffering(@Path("id") String id);

    // Attendance
    @GET("attendance")
    Call<ApiResponse<List<AttendanceDto>>> getAttendance();

    @GET("attendance/student/{id}")
    Call<ApiResponse<List<AttendanceDto>>> getAttendanceByStudent(@Path("id") String studentId);

    @GET("attendance/student/{id}/summary")
    Call<ApiResponse<StudentAttendanceSummaryDto>> getAttendanceSummaryByStudent(@Path("id") String studentId);

    @GET("attendance/session/{id}")
    Call<ApiResponse<List<AttendanceDto>>> getAttendanceBySession(@Path("id") String sessionId);

    @POST("attendance/mark")
    Call<ApiResponse<List<AttendanceDto>>> markAttendance(@Body MarkAttendanceRequest request);

    // Inner classes
    class MarkAttendanceRequest {
        public String sessionId;
        public java.util.List<AttendanceEntry> attendance;
        public MarkAttendanceRequest(String sessionId, java.util.List<AttendanceEntry> attendance) {
            this.sessionId = sessionId;
            this.attendance = attendance;
        }
    }

    class AttendanceEntry {
        public String studentId;
        public String status;
        public AttendanceEntry(String studentId, String status) {
            this.studentId = studentId;
            this.status = status;
        }
    }
}
