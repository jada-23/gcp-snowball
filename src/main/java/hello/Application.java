package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@RestController
public class Application {

    static class Self {
        public String href;
    }

    static class Links {
        public Self self;
    }

    static class PlayerState {
        public Integer x;
        public Integer y;
        public String direction;
        public Boolean wasHit;
        public Integer score;
    }

    static class Arena {
        public List<Integer> dims;
        public Map<String, PlayerState> state;
    }

    static class ArenaUpdate {
        public Links _links;
        public Arena arena;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @GetMapping("/")
    public String index() {
        return "Let the battle begin!";
    }

    public String randomCommand(String[] commands, Random random) {
        return commands[random.nextInt(commands.length)];
    }

    @PostMapping("/**")
    public String index(@RequestBody ArenaUpdate arenaUpdate) {
        System.out.println(arenaUpdate);
        // String[] commands = new String[]{"F", "R", "L", "T"};

        String myUrl = arenaUpdate._links.self.href;
        PlayerState myState = arenaUpdate.arena.state.get(myUrl);
        Random random = new Random();

        if (myState.wasHit) {
            return randomCommand(new String[] { "F", "R" }, random);
        }

        return randomCommand(new String[] { "T", "T", "T", "T", "R" }, random);
    }

}
