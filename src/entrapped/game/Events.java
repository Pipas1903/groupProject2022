package entrapped.game;

import java.util.Random;

public enum Events {

    UPGRADE_TRAP("<html>Oh wait! You found a coin on the floor!<br>With that kind of money, <br>one of your traps is going to be upgraded!</html>", 0),
    LOSE_TRAP("<html>Oh no! You found the terrible Mickey Mouse <br> and he stole one of your traps!</html>", 0),
    THROW_DICE_AGAIN("<html>You just stumbled upon a dice! Throw it!</html>", 0),
    TRIP_ON_SHOE_LACE("<html>You're so silly that tripped on your own shoe lace! <br>That's going to leave a mark!<br>You just lost 15 life points!</html>", 15),
    TASTY_SNACK("<html>Hmm where is this heavenly smell coming from?<br>Oh look! Apple pie just lying here! <br>You just restored 15 life points!</html>", 15),
    WIN_TRAP("<html>You arrived trap forest!<br>You just won a trap!</html>", 0);

    String message;
    int lifePoints;

    Events(String message, int lifePoints) {
        this.message = message;
        this.lifePoints = lifePoints;
    }

    public static Events randomEvent() {
        Random random = new Random();
        double rand = random.nextDouble();

        if (rand < .1) {
            return UPGRADE_TRAP;
        } else if (rand < .2) {
            return WIN_TRAP;
        } else if (rand < .25) {
            return LOSE_TRAP;
        } else if (rand < .35) {
            return THROW_DICE_AGAIN;
        } else if (rand < .55) {
            return TRIP_ON_SHOE_LACE;
        } else {
            return TASTY_SNACK;
        }
    }
}
