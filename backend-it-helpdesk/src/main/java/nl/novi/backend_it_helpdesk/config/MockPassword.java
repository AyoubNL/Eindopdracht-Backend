package nl.novi.backend_it_helpdesk.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MockPassword {

    public String foo(String pass){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode(pass));

        return pass;


    }
//
//    public static void main(String[] args) {
//        mockPass foo = new mockPass();
//        foo.foo("Geheim");
//    }

}
