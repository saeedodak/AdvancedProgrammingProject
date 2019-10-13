package finalexam;

import FLS.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Airplane extends Flyer implements Cloneable, java.io.Serializable {

    private double side1, side2;
    
    Airplane() {}
    
    Airplane(Point l, double vx, double maxFuel, double fuel, double fuelconsumptionrate, double side1, double side2) {
        super(l, vx, maxFuel, fuel, fuelconsumptionrate);
        this.side1 = side1;
        this.side2 = side2;
    }
    
    public void setSide1(double side1) {
        this.side1 = side1;
    }

    public void setSide2(double side2) {
        this.side2 = side2;
    }

    public double getSide1() {
        return this.side1;
    }

    public double getSide2() {
        return this.side2;
    }
    
    @Override
    public String toString() {
        String out = super.toString();
        out += (" side1: " + side1);
        out += (" side2: " + side2);
        return out;
    }
    
    @Override
    public void update(double d) {
        double newX = this.getLocation().getX() + d * this.getVX();
        if(800 < newX || newX < 0) {
            try {
                throw new RangeException("out of range [0, 800]\n");
            }
            catch(RangeException e) {
                System.out.print(e);
            }
            finally {
                this.getLocation().setX((int) newX);
            }
        }
        this.getLocation().setX((int) newX);
        if(Wind.getDirection()*this.getVX() > 0) {
            this.setFuel(this.getFuel() * (1 - (1.0-Wind.getPower()/100.0) * this.getFuelConsumtionRate() * d) );
        }
        else {
            this.setFuel(this.getFuel() * (1 - (1.0+Wind.getPower()/100.0) * this.getFuelConsumtionRate() * d) );
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb((int) (255 * this.getFuel() / this.getMaxFuel()), 0, 0));
        gc.fillRect(
            this.getLocation().getX() - side1/2,
            this.getLocation().getY() - side2/2,
            side1,
            side2
        );
        gc.fillRect(
            this.getLocation().getX() - side2/2,
            this.getLocation().getY() - side1/2,
            side2,
            side1
        );
    }

    @Override
    public boolean isHitByOther(Hittable htbl) {
        Flyer hell = (Flyer) htbl;
        double distance = this.getLocation().dist(hell.getLocation());
        return (distance < 20.0);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Airplane cloned = new Airplane(
                new Point(this.getLocation().getX(), this.getLocation().getY()),
                this.getVX(), this.getMaxFuel(), this.getFuel(),
                this.getFuelConsumtionRate(), this.getSide1(), this.getSide2());
        return cloned;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Airplane)) return false;
        Airplane air = (Airplane) obj;
        return (this.getVX() == air.getVX() && this.getLocation().getY() == air.getLocation().getY());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.side1) ^ (Double.doubleToLongBits(this.side1) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.side2) ^ (Double.doubleToLongBits(this.side2) >>> 32));
        return hash;
    }
    
}