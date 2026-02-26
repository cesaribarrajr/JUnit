
/***
 * JUnit black-box tests for Circle2 (and Circle base class).
 * Uses equivalence partitioning and boundary value analysis.
 * Mirrors Circle1Test since both classes implement the same requirements.
 ***/
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class Circle2Test
{
   private Circle2 circle2;

   @BeforeEach
   public void setup()
   {
      circle2 = new Circle2(1, 2, 3);
   }

   @AfterEach
   public void teardown() {}

   // --- moveBy tests ---

   /** Positive offsets move center correctly in both axes */
   @Test
   public void moveByPositiveOffsets()
   {
      Point p = circle2.moveBy(1, 1);
      assertTrue(p.x == 2 && p.y == 3);
   }

   /** Negative offsets move center correctly in both axes */
   @Test
   public void moveByNegativeOffsets()
   {
      Point p = circle2.moveBy(-1, -1);
      assertTrue(p.x == 0 && p.y == 1);
   }

   /** Zero offset leaves center unchanged */
   @Test
   public void moveByZeroOffset()
   {
      Point p = circle2.moveBy(0, 0);
      assertTrue(p.x == 1 && p.y == 2);
   }

   /** Asymmetric offsets: x and y differ (catches xOffset-used-for-y bug) */
   @Test
   public void moveByAsymmetricOffsets()
   {
      Point p = circle2.moveBy(2, 5);
      assertTrue(p.x == 3 && p.y == 7);
   }

   // --- scale tests ---

   /** Scale by factor > 1 enlarges radius */
   @Test
   public void scaleByFactorGreaterThanOne()
   {
      double r = circle2.scale(2.0);
      assertEquals(6.0, r, 1e-9);
   }

   /** Scale by factor between 0 and 1 shrinks radius */
   @Test
   public void scaleByFractionalFactor()
   {
      double r = circle2.scale(0.5);
      assertEquals(1.5, r, 1e-9);
   }

   /** Scale by 1.0 leaves radius unchanged */
   @Test
   public void scaleByOne()
   {
      double r = circle2.scale(1.0);
      assertEquals(3.0, r, 1e-9);
   }

   /** Negative factor: radius must remain unchanged per spec */
   @Test
   public void scaleByNegativeFactorNoChange()
   {
      double r = circle2.scale(-1.0);
      assertEquals(3.0, r, 1e-9);
   }

   // --- intersects tests ---

   /** Circles clearly overlapping */
   @Test
   public void intersectsOverlappingCircles()
   {
      Circle2 other = new Circle2(2, 2, 3);
      assertTrue(circle2.intersects(other));
   }

   /** Circles clearly far apart, no intersection */
   @Test
   public void intersectsDistantCircles()
   {
      Circle2 other = new Circle2(100, 100, 1);
      assertFalse(circle2.intersects(other));
   }

   /** Circles exactly touching at one point (boundary: d == r1 + r2) */
   @Test
   public void intersectsExactlyTouching()
   {
      // circle2 center(1,2) r=3; other center(7,2) r=3; distance=6=3+3
      Circle2 other = new Circle2(7, 2, 3);
      assertTrue(circle2.intersects(other));
   }

   /** Circles just barely not touching (d slightly > r1 + r2) */
   @Test
   public void intersectsJustNotTouching()
   {
      Circle2 other = new Circle2(7.01, 2, 3);
      assertFalse(circle2.intersects(other));
   }

   /** A circle intersects itself */
   @Test
   public void intersectsWithItself()
   {
      assertTrue(circle2.intersects(circle2));
   }

   /** Intersect is symmetric: A.intersects(B) == B.intersects(A) */
   @Test
   public void intersectsIsSymmetric()
   {
      Circle2 other = new Circle2(4, 2, 1);
      assertEquals(circle2.intersects(other), other.intersects(circle2));
   }

   /** One circle completely inside another still counts as intersecting */
   @Test
   public void intersectsOneInsideOther()
   {
      Circle2 inner = new Circle2(1, 2, 1);
      assertTrue(circle2.intersects(inner));
   }
}
