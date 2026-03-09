// Factory Design Pattern
package design_patterns;
// Burger Factory Example
// ============================

// ---------- PRODUCT INTERFACE ----------
interface Burger {

    // Every burger must implement these methods
    void prepare();
    void cook();
    void pack();
}

// ---------- CONCRETE PRODUCTS ----------

// Veg Burger
class VegBurger implements Burger {

    @Override
    public void prepare() {
        System.out.println("Preparing Veg Burger ingredients");
    }

    @Override
    public void cook() {
        System.out.println("Cooking Veg Burger");
    }

    @Override
    public void pack() {
        System.out.println("Packing Veg Burger");
    }
}

// Chicken Burger
class ChickenBurger implements Burger {

    @Override
    public void prepare() {
        System.out.println("Preparing Chicken Burger ingredients");
    }

    @Override
    public void cook() {
        System.out.println("Cooking Chicken Burger");
    }

    @Override
    public void pack() {
        System.out.println("Packing Chicken Burger");
    }
}

// Cheese Burger
class CheeseBurger implements Burger {

    @Override
    public void prepare() {
        System.out.println("Preparing Cheese Burger ingredients");
    }

    @Override
    public void cook() {
        System.out.println("Cooking Cheese Burger");
    }

    @Override
    public void pack() {
        System.out.println("Packing Cheese Burger");
    }
}

// ---------- FACTORY CLASS ----------
class BurgerFactory {

    // Factory method that creates burger objects
    public static Burger orderBurger(String type) {

        Burger burger;

        // Decide which burger to create
        if (type.equalsIgnoreCase("veg")) {
            burger = new VegBurger();
        } else if (type.equalsIgnoreCase("chicken")) {
            burger = new ChickenBurger();
        } else if (type.equalsIgnoreCase("cheese")) {
            burger = new CheeseBurger();
        } else {
            throw new IllegalArgumentException("Invalid burger type");
        }

        // Common operations for all burgers
        burger.prepare();
        burger.cook();
        burger.pack();

        return burger;
    }
}

// ---------- CLIENT ----------
public class factory_design {

    public static void main(String[] args) {

        // Client does NOT use 'new' keyword directly
        Burger vegBurger = BurgerFactory.orderBurger("veg");
        System.out.println();

        Burger chickenBurger = BurgerFactory.orderBurger("chicken");
        System.out.println();

        Burger cheeseBurger = BurgerFactory.orderBurger("cheese");
    }
}
