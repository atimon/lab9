/* CS118, Lab Week 6
   An Introduction to Object Oriented Programming
   Sara Kalvala, 2018.11
*/

import java.awt.Color;
import java.util.Scanner;

// Figure is abstract because some methods are abstract, ie there is no code given,
// only the type, which needs to be followed in subclasses

// As Color is protected, we create set and get methods for it, as the only way to change them.

abstract class Figure {
    protected Color color = Color.blue;
    public void setColor(Color c) {color = c;}
    public Color getColor() {return color;}
    public abstract double area();
    public abstract String toString();
    public abstract void print(int format);
}

// We create protected data fields below so that subclasses can access them directly
// (as opposed to private, where subclasses can't see them.)

// We do not *have* to create get/set methods, if we do not want other classes to have
// access to those data fields

class Triangle extends Figure {
    protected int side1;
    protected int side2;
    protected int side3;

    public Triangle(int s1, int s2, int s3) {
      side1 = s1;
      side2 = s2;
      side3 = s3;
    }

    public double area() {
        int total = side1 + side2 + side3;
        float s = (total/2f);
        double result = Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
        return result;
    }

    public String toString() {return ("Triangle of area "+area());}
    public void print(int format){
        double height = ((2 * area())/ side1)/10;
        double difference = ((1-height)/2);
        double base = (side1/10f);
        double p1x = ((1-base)/2);
        double p2x = (p1x+base);
        double p12y = difference;
        double p3x = p1x+(Math.sqrt(((side2/10f)*(side2/10f))-(height*height)));
        double p3y = 1-difference;
        double xvalues[] = {p1x, p2x, p3x};
        double yvalues[] = {p12y, p12y, p3y};
        if (format == 1) {
            StdDraw.filledPolygon(xvalues, yvalues);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.polygon(xvalues, yvalues);
        }
        else {
            StdDraw.polygon(xvalues, yvalues);
        }
    }
}

class Isosceles extends Triangle {

    protected int side1;
    protected int side2;
    public Isosceles(int x, int y){super (x,y,y); side1 = x; side2=y;}
    public String toString() {return ("An isosceles triangle of area "+area());}
}

class Equilateral extends Isosceles {

    protected int side;
    public Equilateral(int s){super(s,s); side = s;}
    public String toString() {return ("An equilateral triangle of area "+area());}
}

class Rectangle extends Figure {

    protected int height;
    protected int width;

    public Rectangle(int h, int w) {
	height = h; width=w;}

    public double area() {return height * width;}
    public String toString() {return ("Rectangle of area "+area());}
    public void print(int format){
        if (format == 1) {
            StdDraw.filledRectangle(0.5, 0.5, (this.width/20f), (this.height/20f));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(0.5, 0.5, (this.width/20f), (this.height/20f));
        }
        else {
            StdDraw.rectangle(0.5, 0.5, (this.width/20f), (this.height/20f));
        }
    }
}


class Square extends Rectangle {

    protected int side;
    public Square(int s){ super(s,s); side = s;}
    public double area() {return side * side;}
    public String toString() {return ("A square of area "+area());}
}

// Because side in Square is protected, not private, we are able to access it directly
// in the subclass below using super.side, even though there is no get method.

class MutableSquare extends Square {
    public MutableSquare(int s){ super(s);}
    public void Resize (int x) {super.side = x;}
}

// Ellipse, Circle and MutableSquare are analogous to the above.

class Ellipse extends Figure {

    protected int axis1;
    protected int axis2;

    public Ellipse(int h, int w) {
	axis1 = h; axis2 = w;}

    public double area() {return Math.PI * axis1 * axis2;}
    public String toString() {return ("Ellipse of area "+ area());}
    public void print(int format){
        if (format ==  1) {
            StdDraw.filledEllipse(0.5, 0.5, (this.axis1/20f), (this.axis2/20f));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.ellipse(0.5, 0.5, (this.axis1/20f), (this.axis2/20f));
        }
        else {
            StdDraw.ellipse(0.5, 0.5, (this.axis1/20f), (this.axis2/20f));
        }
    }
}


class Circle extends Ellipse {

    protected int radius;
    public Circle(int s){ super(s,s); radius = s;}
    public double area() {return Math.PI * Math.pow(radius, 2);}
    public String toString() {return ("A circle of area "+ area());}
}


class MutableCircle extends Circle {
    public MutableCircle(int s){ super(s);}
    public void Resize (int x) {super.radius = x;}
}

public class Program {

    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
        StdDraw.setPenRadius(0.01);
    	// just initialized it to something to avoid errors;
    	Figure fig =  new Circle(25);
        char command = ' ';
        System.out.print("\033[H\033[2J");
        System.out.flush();
    	System.out.println("Enter shape and sizes or press h for help");
    	command = sc.next().charAt(0);
    	while (command != 'x') {
            StdDraw.clear();
    	    switch (command) {
    	    case 'r': fig = new Rectangle(sc.nextInt(),sc.nextInt()); break;
    	    case 's': fig = new Square(sc.nextInt()); break;
            case 'e': fig = new Ellipse(sc.nextInt(),sc.nextInt()); break;
            case 'c': fig = new Circle(sc.nextInt()); break;
            case 't': fig = new Triangle(sc.nextInt(),sc.nextInt(),sc.nextInt()); break;
            case 'i': fig = new Isosceles(sc.nextInt(),sc.nextInt()); break;
            case 'q': fig = new Equilateral(sc.nextInt()); break;
            case 'h': helpshape();
    	    }
            if (command != 'h') {
                char clr;
                do {
                    System.out.println("Choose the color of the shape or type l for list");
                    clr = sc.next().charAt(0);
                    switch (clr) {
                        case 'b': StdDraw.setPenColor(StdDraw.BLUE); break;
                        case 'r': StdDraw.setPenColor(StdDraw.RED); break;
                        case 'p': StdDraw.setPenColor(StdDraw.PINK); break;
                        case 'y': StdDraw.setPenColor(StdDraw.YELLOW); break;
                        case 'w': StdDraw.setPenColor(StdDraw.WHITE); break;
                        case 'g': StdDraw.setPenColor(StdDraw.GREEN); break;
                        case 'o': StdDraw.setPenColor(StdDraw.ORANGE); break;
                        case 'a': System.out.println(fig.toString()); break;
                        case 'l': colorlist(); break;
                        default: StdDraw.setPenColor(StdDraw.BLACK); break;
                    }
                } while (clr == 'l'||clr == 'a');
                System.out.println("Do you want the shape to be filled? (y/n)");
                int decision = sc.next().charAt(0);
                int flld = (decision == 'y') ? 1 : 2;
                fig.print(flld);
            }
            if (command != 'h') {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
    	    System.out.println("Enter shape and sizes or press h for help");
    	    command = sc.next().charAt(0);
    	}
	}
    public static void helpshape() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(ConsoleColors.BLACK_BOLD + "Welcome!" + ConsoleColors.RESET);
        System.out.println(" ");
        System.out.println("This is a program to print the shapes you specify!");
        System.out.println("It currently supports triangles, circles and squares.");
        System.out.println(" ");
        System.out.print("To print a " + ConsoleColors.BLUE_BOLD + "Rectangle");
        System.out.println(ConsoleColors.RESET + " simply type in the letter " + ConsoleColors.BLUE_BOLD + "r" + ConsoleColors.RESET + " and the height and width of the figure.");
        System.out.print("To print a " + ConsoleColors.BLUE_BOLD + "Square");
        System.out.println(ConsoleColors.RESET + " simply type in the letter " + ConsoleColors.BLUE_BOLD + "s" + ConsoleColors.RESET + " and the length of one of it's sides.");
        System.out.print("To print an " + ConsoleColors.BLUE_BOLD + "Ellipse");
        System.out.println(ConsoleColors.RESET + " simply type in the letter " + ConsoleColors.BLUE_BOLD + "e" + ConsoleColors.RESET + " and the length of the two axis.");
        System.out.print("To print a " + ConsoleColors.BLUE_BOLD + "Circle");
        System.out.println(ConsoleColors.RESET + " simply type in the letter " + ConsoleColors.BLUE_BOLD + "c" + ConsoleColors.RESET + " and the radius of the shape.");
        System.out.print("To print a " + ConsoleColors.BLUE_BOLD + "Triangle");
        System.out.println(ConsoleColors.RESET + " simply type in the letter " + ConsoleColors.BLUE_BOLD + "t" + ConsoleColors.RESET + " and the legths of it's three sides.");
        System.out.print("To print an " + ConsoleColors.BLUE_BOLD + "Isosceles Triangle");
        System.out.println(ConsoleColors.RESET + " simply type in the letter " + ConsoleColors.BLUE_BOLD + "i" + ConsoleColors.RESET + " and the legths of it's two different sides.");
        System.out.print("To print an " + ConsoleColors.BLUE_BOLD + "Equilateral Triangle");
        System.out.println(ConsoleColors.RESET + " simply type in the letter " + ConsoleColors.BLUE_BOLD + "q" + ConsoleColors.RESET + " and the legth if it's sides.");
        System.out.println(ConsoleColors.BLACK_UNDERLINED + "Lengths" + ConsoleColors.RESET + " should be a number between 0 and 10.");
        System.out.println(ConsoleColors.BLACK_UNDERLINED + "Remember" + ConsoleColors.RESET + " to use only non-capital letters and to leave a space between each input.");
        System.out.println(" ");
        System.out.print("To " + ConsoleColors.RED_BOLD + "EXIT");
        System.out.println(ConsoleColors.RESET + " simply close the pop-up window!" + ConsoleColors.RESET);
        System.out.println(" ");
        System.out.println("----------------------------------");
        System.out.println(" ");
    }

    public static void colorlist() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(" ");
        System.out.println("You can choose the following colors: ");
        System.out.println(ConsoleColors.BLACK_BOLD + "'b'" + ConsoleColors.RESET + " for " + ConsoleColors.BLUE_UNDERLINED + "blue" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'r'" + ConsoleColors.RESET + " for " + ConsoleColors.RED_UNDERLINED + "red" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'p'" + ConsoleColors.RESET + " for " + ConsoleColors.PURPLE_UNDERLINED + "pink" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'y'" + ConsoleColors.RESET + " for " + ConsoleColors.YELLOW_UNDERLINED + "yellow" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'w'" + ConsoleColors.RESET + " for " + ConsoleColors.WHITE_UNDERLINED + ConsoleColors.BLACK_BACKGROUND + "white" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'g'" + ConsoleColors.RESET + " for " + ConsoleColors.GREEN_UNDERLINED + "green" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'o'" + ConsoleColors.RESET + " for " + ConsoleColors.YELLOW_UNDERLINED + "orange" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'k'" + ConsoleColors.RESET + " for " + ConsoleColors.BLACK_UNDERLINED + "black" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BOLD + "'a'" + ConsoleColors.RESET + " to see " + "the area of your shape" + ConsoleColors.RESET);
        System.out.println(" ");
    }
}

class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
}


	/* Notice the slight trickery here: because we have a method defined
           in MutableSquare but not in Figure, we allow the same object to have
           two names with different types, so we can see an individual object as
	   a member of a class or a subclass as needed.
	MutableSquare f1 =   new MutableSquare(45);
	Figure f2 = f1;
	System.out.println(f2.toString());
	// Another way to print, allowing formatting of values
	System.out.format("and area %.2f.\n", f2.area());
	f1.Resize(25);
	System.out.println(f2.toString());
	// Preview of how we can use command line arguments
	if (args.length > 0) {
	    System.out.println(args[0]);}
    } */
