package tvg.game;

import java.util.Random;

public enum Events {

    UPGRADE_TRAP("Oh wait! You found a coin on the floor! \nWith that kind of money, one of your traps is going to be upgraded!", 0),
    LOSE_TRAP("Oh no! You found the terrible Mickey Mouse and he stole one of your traps!", 0),
    THROW_DICE_AGAIN("You just stumbled upon a dice! Throw it!", 0),
    TRIP_ON_SHOE_LACE("You're so silly that tripped on your own show lace! \nThat's going to leave a mark!\nYou just lost 15 life points!", 15),
    TASTY_SNACK("Hmm where is this heavenly smell coming from?\nOh look! Apple pie just lying here! \nYou just restored 15 life points!", 15),
    WIN_TRAP("You arrived trap forest!\nYou just won a trap!", 0);

    String message;
    int lifePoints;

    Events(String message, int lifePoints) {
        this.message = message;
        this.lifePoints = lifePoints;
    }

    public String getMessage() {
        return message;
    }

    public int getLifePoints() {
        return lifePoints;
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
