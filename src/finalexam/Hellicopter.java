package finalexam;

import FLS.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

public class Hellicopter extends Flyer implements Cloneable, java.io.Serializable  {

    private double side1, side2;
    private int upBound, dwBound;
    
    Hellicopter() {}
    
    Hellicopter(Point l, double vx, double maxFuel, double fuel, double fuelconsumptionrate, double side1, double side2, int upBound, int dwBound) {
        super(l, vx, maxFuel, fuel, fuelconsumptionrate);
        this.side1 = side1;
        this.side2 = side2;
        this.upBound = upBound;
        this.dwBound = dwBound;
    }
    
    public void setUpBound(int upBound) {
        this.upBound = upBound;
    }
    
    public void setDwBound(int dwBound) {
        this.dwBound = dwBound;
    }
    
    public void setSide1(double side1) {
        this.side1 = side1;
    }

    public void setSide2(double side2) {
        this.side2 = side2;
    }
    
    public int getUpBound() {
        return this.upBound;
    }

    public int getDwBound() {
        return this.dwBound;
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
        out += (" upBound: " + upBound);
        out += (" dwBound: " + dwBound);
        return out;
    }
    
    @Override
    public void update(double d) {
        double Time = d;
        double newX = this.getLocation().getX();
        while(true) {
            double ntX = newX + Time * this.getVX();
            if(ntX > upBound) {
                Time -= (upBound - newX) / Math.abs(this.getVX());
                newX = upBound;
                this.setVX(-this.getVX());
            }
            else if(ntX < dwBound) {
                Time -= (newX - dwBound) / Math.abs(this.getVX());
                newX = dwBound;
                this.setVX(-this.getVX());
            }
            else {
                newX = ntX;
                break;
            }
        }
        getLocation().setX((int) newX);
        if(Wind.getDirection()*this.getVX() > 0) {
            this.setFuel(this.getFuel() * (1 - (1.0-Wind.getPower()/100.0) * this.getFuelConsumtionRate() * d) );
        }
        else {
            this.setFuel(this.getFuel() * (1 - (1.0+Wind.getPower()/100.0) * this.getFuelConsumtionRate() * d) );
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, (int) (255 * this.getFuel() / this.getMaxFuel())));
        gc.fillRect(
            this.getLocation().getX() - side1/2,
            this.getLocation().getY() - side2/2,
            side1,
            side2*3/2
        );
        gc.fillOval(
            this.getLocation().getX() - side1/2 - side1,
            this.getLocation().getY(),
            side1,
            side1
        );
        gc.fillOval(
            this.getLocation().getX() + side1/2,
            this.getLocation().getY(),
            side1,
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
        Hellicopter cloned = new Hellicopter(
                new Point(this.getLocation().getX(), this.getLocation().getY()),
                this.getVX(), this.getMaxFuel(), this.getFuel(),
                this.getFuelConsumtionRate(), this.getSide1(), this.getSide2(),
                this.upBound, this.dwBound);
        return cloned;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Hellicopter)) return false;
        Hellicopter hell = (Hellicopter) obj;
        return (this.getVX() == hell.getVX() &&
                this.getUpBound() == hell.getUpBound() &&
                this.getDwBound() == hell.getDwBound() &&
                this.getLocation().getY() == hell.getLocation().getY());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.side1) ^ (Double.doubleToLongBits(this.side1) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.side2) ^ (Double.doubleToLongBits(this.side2) >>> 32));
        hash = 37 * hash + this.upBound;
        hash = 37 * hash + this.dwBound;
        return hash;
    }
    
}