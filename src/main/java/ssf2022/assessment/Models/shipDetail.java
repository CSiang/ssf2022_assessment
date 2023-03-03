package ssf2022.assessment.Models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class shipDetail {
    
    @NotNull(message="Please state your name")
	@Size(min=2, message="Your name must be longer than 2 characters")
    private String name;

    @NotNull(message="Please state your address")
    private String address;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "shipDetail [name=" + name + ", address=" + address + "]";
    }

    

}
