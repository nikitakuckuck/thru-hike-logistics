package learn.thruhike.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate (@RequestBody Map<String, String> credentials){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.get("username"),credentials.get("password"));

                try {
                    Authentication authentication = authenticationManager.authenticate(authenticationToken);
                    if(authentication.isAuthenticated()){
                        HashMap<String,String> map = new HashMap<>();
                        return new ResponseEntity<>(map,HttpStatus.OK);
                    }
                } catch (AuthenticationException ex){
                    System.out.println(ex);
                }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
