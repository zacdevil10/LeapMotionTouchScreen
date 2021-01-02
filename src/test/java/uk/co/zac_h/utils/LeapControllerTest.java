package uk.co.zac_h.utils;

import com.leapmotion.leap.Vector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LeapControllerTest {

    private ArrayList<Vector> coordinates = new ArrayList<>();
    private Vector position;

    private LeapController leapController = new LeapController();

    @Before
    public void setUp() {
        Vector vectorLeft = new Vector(-400, 500, 0);
        Vector vectorRight = new Vector(400, 500, 0);
        Vector vectorBottom = new Vector(0f, 180, 0);

        coordinates.add(vectorLeft);
        coordinates.add(vectorRight);
        coordinates.add(vectorBottom);

        position = new Vector(-120, 235, 17);
    }

    @Test
    public void scale() {
        assertEquals(0.35, leapController.scale(position, coordinates).getX(), 0.00000001);
    }
}