package com.hrafty.web_app.Auth;


import com.hrafty.web_app.Auth.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping(path = "/register-seller")
    public ResponseEntity<Object> registerSeller(@RequestBody AuthDTO authDTO) throws Exception {
        Object obj =authService.registerSeller(authDTO.getUser(),authDTO.getSeller());
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }
    @PostMapping(path = "/register-customer")
    public ResponseEntity<Object> registerCustomer(@RequestBody AuthDTO responseDTO) throws Exception {
        Object obj =authService.registerCustomer(responseDTO.getUser(),responseDTO.getCustomer());
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request){
        Object object=authService.login(request.getEmail(), request.getPassword());
       return ResponseEntity.ok(object);
    }


}
