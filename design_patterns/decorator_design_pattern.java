package design_patterns;

/*
============================================================
DECORATOR DESIGN PATTERN - COMPLETE EXPLANATION IN CODE
============================================================

PROBLEM SCENARIO
----------------
Suppose we are designing a Pizza ordering system.

Base pizzas:
    - Margherita
    - FarmHouse
    - VegDelight

Toppings:
    - Extra Cheese
    - Mushroom
    - Olives

Customers can combine them in many ways:
    Margherita + Cheese
    Margherita + Cheese + Mushroom
    FarmHouse + Cheese
    VegDelight + Olives + Mushroom + Cheese

If we try to create a separate class for every combination,
the number of classes explodes.

Example:
    MargheritaWithCheese
    MargheritaWithCheeseAndMushroom
    MargheritaWithCheeseAndOlives
    FarmHouseWithCheese
    etc...

This leads to a CLASS EXPLOSION problem.

Decorator pattern solves this by allowing us to
ADD BEHAVIOR DYNAMICALLY at runtime.

Instead of creating new classes for combinations,
we wrap objects with decorators.

This is based on:
    "HAS-A relationship instead of IS-A relationship"
*/


public class decorator_design_pattern {


    /*
    ============================================================
    STEP 1 : COMPONENT INTERFACE
    ============================================================

    This is the base interface that all objects will implement.

    Both:
        - Base pizza
        - Toppings (decorators)

    will implement this.

    This ensures all objects have a common method.
    */
    interface BasePizza {

        // Method to return total cost of pizza
        int cost();
    }



    /*
    ============================================================
    STEP 2 : CONCRETE COMPONENTS (BASE OBJECTS)
    ============================================================

    These are the basic pizza types.
    They implement BasePizza directly.
    */

    // Margherita Pizza
    static class Margherita implements BasePizza {

        @Override
        public int cost() {

            // Base price of Margherita
            return 100;
        }
    }


    // FarmHouse Pizza
    static class FarmHouse implements BasePizza {

        @Override
        public int cost() {

            // Base price of FarmHouse
            return 200;
        }
    }


    // Veg Delight Pizza
    static class VegDelight implements BasePizza {

        @Override
        public int cost() {

            return 150;
        }
    }



    /*
    ============================================================
    STEP 3 : DECORATOR ABSTRACT CLASS
    ============================================================

    This class is the heart of the decorator pattern.

    It implements BasePizza so that decorators can be treated
    exactly like pizzas.

    It also contains a reference to BasePizza.

    This allows decorators to wrap around other pizzas or decorators.

    Example wrapping chain:

    Cheese(
        Mushroom(
            Margherita
        )
    )
    */

    static abstract class ToppingDecorator implements BasePizza {

        // Reference to the pizza being decorated
        BasePizza pizza;

        // Constructor
        ToppingDecorator(BasePizza pizza) {

            this.pizza = pizza;
        }
    }



    /*
    ============================================================
    STEP 4 : CONCRETE DECORATORS
    ============================================================

    These classes add extra functionality.

    Each topping decorator:
        - wraps another pizza
        - adds extra cost
    */

    // Extra Cheese Topping
    static class ExtraCheese extends ToppingDecorator {

        // Constructor passes pizza object to parent
        ExtraCheese(BasePizza pizza) {
            super(pizza);
        }

        @Override
        public int cost() {

            /*
            Workflow:

            1. Call cost() of wrapped pizza
            2. Add cheese cost
            */

            return pizza.cost() + 20;
        }
    }



    // Mushroom Topping
    static class Mushroom extends ToppingDecorator {

        Mushroom(BasePizza pizza) {
            super(pizza);
        }

        @Override
        public int cost() {

            return pizza.cost() + 30;
        }
    }



    // Olive Topping
    static class Olives extends ToppingDecorator {

        Olives(BasePizza pizza) {
            super(pizza);
        }

        @Override
        public int cost() {

            return pizza.cost() + 25;
        }
    }




    /*
    ============================================================
    STEP 5 : CLIENT CODE
    ============================================================

    This is where we dynamically create combinations.
    */

    public static void main(String[] args) {


        /*
        Example 1:
        Margherita + Extra Cheese
        */

        BasePizza pizza1 =
                new ExtraCheese(
                        new Margherita()
                );

        System.out.println("Cost of Margherita + Cheese = " + pizza1.cost());



        /*
        Example 2:
        Margherita + Mushroom + Cheese
        */

        BasePizza pizza2 =
                new ExtraCheese(
                        new Mushroom(
                                new Margherita()
                        )
                );

        System.out.println("Cost of Margherita + Mushroom + Cheese = " + pizza2.cost());



        /*
        Example 3:
        FarmHouse + Cheese + Olives
        */

        BasePizza pizza3 =
                new Olives(
                        new ExtraCheese(
                                new FarmHouse()
                        )
                );

        System.out.println("Cost of FarmHouse + Cheese + Olives = " + pizza3.cost());
    }
}



/*
============================================================
RUNTIME WORKFLOW (VERY IMPORTANT)
============================================================

Example:

BasePizza pizza =
        new ExtraCheese(
            new Mushroom(
                new Margherita()
            )
        );

Execution flow:

Step 1:
Margherita.cost()
returns 100

Step 2:
Mushroom.cost()
= pizza.cost() + 30
= 100 + 30
= 130

Step 3:
ExtraCheese.cost()
= pizza.cost() + 20
= 130 + 20
= 150

Final cost = 150


============================================================
STRUCTURE
============================================================

          BasePizza (Component)
               |
      -----------------------
      |                     |
   Margherita         FarmHouse
   (Concrete)         (Concrete)

               |
        ToppingDecorator
               |
      ------------------------
      |          |           |
  ExtraCheese   Mushroom    Olives
  (Decorator)  (Decorator) (Decorator)



============================================================
KEY IDEA
============================================================

Decorators wrap objects and add functionality dynamically.

Instead of:

MargheritaWithCheese
MargheritaWithCheeseAndMushroom

We create:

new Cheese(new Mushroom(new Margherita()))


============================================================
REAL WORLD EXAMPLES
============================================================

1️⃣ Java IO Streams

BufferedReader(
    InputStreamReader(
        FileInputStream
    )
)

Each wrapper adds behavior.


2️⃣ Spring Security Filters

Filter(
   Filter(
      Filter(
         Request
      )
   )
)

3️⃣ UI frameworks

ScrollBar(
   Window
)


============================================================
BENEFITS
============================================================

1. No class explosion
2. Add behavior dynamically
3. Open Closed Principle
4. Flexible combinations


============================================================
INTERVIEW TIP
============================================================

Decorator pattern is commonly asked in LLD interviews with:

• Pizza toppings
• Coffee with add-ons
• Notification systems
• Java IO streams
• Middleware chains

============================================================
*/