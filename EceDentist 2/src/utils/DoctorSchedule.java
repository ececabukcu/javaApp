package utils;

import java.util.Date;

public class DoctorSchedule {
    // Burada DoctorSchedule sınıfının özelliklerini tanımlayın
    // Örneğin, gün, saat gibi bilgiler burada yer alabilir.

    private String day;
    private String time;

    public DoctorSchedule(String day, String time) {
        this.day = day;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
