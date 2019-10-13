package finalexam;

import FLS.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import java.io.ObjectInputStream;
import javafx.scene.canvas.Canvas;
import java.io.ObjectOutputStream;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import static javafx.application.Application.launch;

public class FinalExam extends Application {
    
    private boolean pauseHited = false;
    
    @Override
    public void start(Stage primaryStage) {
        int size = 10;
        Flyer [] fly = new Flyer[size];
        Boolean [] exist = new Boolean[size];
        for(int i=0; i<size; i++) {
            if(rand(1) == 0) {
                fly[i] = new Airplane(new Point(rand(20, 520), rand(20, 520)), rand(5, 10), 200, rand(100, 150), 0.05, rand(10, 12), rand(18, 20));
            }
            else {
                fly[i] = new Hellicopter(new Point(rand(10, 520), rand(10, 520)), rand(10, 15), 200, rand(100, 150), 0.05, rand(10, 12), rand(18, 20), rand(500, 800), rand(100, 300));
            }
            exist[i] = false;
        }
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fly & Fly :) !!!");
        Canvas canvas = new Canvas(800, 550);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Button btn1 = new Button("start");
        Button btn2 = new Button("pause");
        Button btn3 = new Button("load");
        Button btn4 = new Button("save");
        Button btn5 = new Button("exit");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pauseHited == true) {
                    pauseHited = false;
                }
            }
        });
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(pauseHited == false) {
                    pauseHited = true;
                }
            }
        });
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pauseHited = true;
                for(int i=0; i<size; i++) {
                    try {
			FileReader fr = new FileReader("./files/flyer" + (i) + ".ser");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
                        if(line.charAt(0) == '1') {
                            line = br.readLine();
                            int xx = Integer.parseInt(line);
                            line = br.readLine();
                            int yy = Integer.parseInt(line);
                            Point pt = new Point(xx, yy);
                            line = br.readLine();
                            double vx = Double.parseDouble(line);
                            line = br.readLine();
                            double mxfu = Double.parseDouble(line);
                            line = br.readLine();
                            double fu = Double.parseDouble(line);
                            line = br.readLine();
                            double rate = Double.parseDouble(line);
                            line = br.readLine();
                            double s1 = Double.parseDouble(line);
                            line = br.readLine();
                            double s2 = Double.parseDouble(line);
                            fly[i] = new Airplane(pt, vx, mxfu, fu, rate, s1, s2);
                        }
                        else {
                            line = br.readLine();
                            int xx = Integer.parseInt(line);
                            line = br.readLine();
                            int yy = Integer.parseInt(line);
                            Point pt = new Point(xx, yy);
                            line = br.readLine();
                            double vx = Double.parseDouble(line);
                            line = br.readLine();
                            double mxfu = Double.parseDouble(line);
                            line = br.readLine();
                            double fu = Double.parseDouble(line);
                            line = br.readLine();
                            double rate = Double.parseDouble(line);
                            line = br.readLine();
                            double s1 = Double.parseDouble(line);
                            line = br.readLine();
                            double s2 = Double.parseDouble(line);
                            line = br.readLine();
                            int up = Integer.parseInt(line);
                            line = br.readLine();
                            int dw = Integer.parseInt(line);
                            fly[i] = new Hellicopter(pt, vx, mxfu, fu, rate, s1, s2, up, dw);
                        }
                        br.close();
                        fr.close();
                    } 
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        FileInputStream file = new FileInputStream("./files/exist" + (i) + ".ser");
                        ObjectInputStream in = new ObjectInputStream(file);
                        exist[i] = (Boolean) in.readObject();
                        in.close();
                        file.close();
                    }
                    catch(IOException ex) {
                        ex.printStackTrace();
                    }
                    catch (ClassNotFoundException ex) {
                        Logger.getLogger(FinalExam.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                pauseHited = false;
            }
        });
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i=0; i<size; i++) {
                    try {
                        FileWriter fw = new FileWriter("./files/flyer" + (i) + ".ser");
                        BufferedWriter bw = new BufferedWriter(fw);
                        if(fly[i] instanceof Airplane) {
                            Airplane airplane = (Airplane) fly[i];
                            bw.write("1\n");
                            bw.write(airplane.getLocation().getX() + "\n");
                            bw.write(airplane.getLocation().getY() + "\n");
                            bw.write("" + airplane.getVX() + "\n");
                            bw.write("" + airplane.getMaxFuel() + "\n");
                            bw.write("" + airplane.getFuel() + "\n");
                            bw.write("" + airplane.getFuelConsumtionRate() + "\n");
                            bw.write("" + airplane.getSide1() + "\n");
                            bw.write("" + airplane.getSide2() + "\n");
                        }
                        else {
                            Hellicopter hellicopter = (Hellicopter) fly[i];
                            bw.write("2\n");
                            bw.write(hellicopter.getLocation().getX() + "\n");
                            bw.write(hellicopter.getLocation().getY() + "\n");
                            bw.write("" + hellicopter.getVX() + "\n");
                            bw.write("" + hellicopter.getMaxFuel() + "\n");
                            bw.write("" + hellicopter.getFuel() + "\n");
                            bw.write("" + hellicopter.getFuelConsumtionRate() + "\n");
                            bw.write("" + hellicopter.getSide1() + "\n");
                            bw.write("" + hellicopter.getSide2() + "\n");
                            bw.write("" + hellicopter.getUpBound()+ "\n");
                            bw.write("" + hellicopter.getDwBound()+ "\n");
                        }
                        bw.close();
                        fw.close();
                    } 
                    catch(IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        FileOutputStream fileOut = new FileOutputStream("./files/exist" + (i) + ".ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(exist[i]);
                        out.close();
                        fileOut.close();
                    }
                    catch(IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        HBox hbox = new HBox();
        hbox.setLayoutX(560);
        hbox.setLayoutY(515);
        hbox.setSpacing(5);
        hbox.getChildren().add(btn1);
        hbox.getChildren().add(btn2);
        hbox.getChildren().add(btn3);
        hbox.getChildren().add(btn4);
        hbox.getChildren().add(btn5);
        root.getChildren().add(hbox);
        
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                if(pauseHited == false) {
                    gc.clearRect(0, 0, 800, 550);
                    for(int i=0; i<size; i++) {
                        if(exist[i] == false) {
                            fly[i].update(0.2);
                            fly[i].draw(gc);
                        }
                    }
                    for(int i=0; i<size; i++) {
                        if(fly[i].getFuel() < 0.5) exist[i] = true;
                    }
                    for(int i=0; i<size; i++) {
                        if(exist[i]) continue;
                        boolean flag = false;
                        for(int j=i+1; j<size; j++) {
                            if(exist[j] == false) {
                                if(fly[i].isHitByOther(fly[j])) {
                                    flag = true;
                                    exist[j] = true;
                                }
                            }
                        }
                        exist[i] = flag;
                    }
                }
                else {
                    gc.clearRect(0, 0, 800, 550);
                    for(int i=0; i<size; i++) {
                        if(exist[i] == false) {
                            fly[i].draw(gc);
                        }
                    }
                }
            }
        }.start();
        
        primaryStage.show();
    }

    private int rand(int i) {
        return rand(0, i);
    }
    
    private int rand(int i, int j) {
        int r = (int) (Math.random() * 1000000);
        return r % (j - i + 1) + i;
    }
    
    public static void main(String[] args) throws CloneNotSupportedException {
        launch(args);
    }
    
}