package ru.malichenko.sobSpring;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/hello")
    public String getHello() {
        return "get hello";
    }

    @PostMapping("/hello")
    public String postHello() {
        return "post hello";
    }

    @GetMapping("/get_bob")
    public Person getNewPerson() {
        return new Person("Bob");
    }

    @GetMapping
    public Person getPersonModel(@ModelAttribute Person person) {
        return person;
    }

    @PostMapping("/json")
    public String postSimpleJson(@RequestBody Person person) {
        return person.getName();
    }

}
