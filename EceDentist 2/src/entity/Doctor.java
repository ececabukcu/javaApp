package entity;

public class Doctor {
    private Integer id;
    private String name;
    private Integer phone;
    private String gender;
    private String doctorSpecialty;


    public Doctor(Integer id, String name, Integer phone, String gender, String doctorSpecialty ) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.doctorSpecialty = doctorSpecialty;

    }

    public Doctor(String name, Integer phone, String gender, String doctorSpecialty) {
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.doctorSpecialty = doctorSpecialty;
    }

    public Doctor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDoctorSpecialty() {
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }


}
