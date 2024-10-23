package com.playground.kafkaplayground;

import com.playground.kafkaplayground.domain.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> products;

    public ProductController() {
        this.products = initializeProducts();
    }

    @GetMapping
    public List<Product> getProducts() {
        return products;
    }

    private List<Product> initializeProducts() {
        List<Product> products = new ArrayList<>() {{
            add(new Product(1L, "Tennis Racket"));
            add(new Product(2L, "Basketball"));
            add(new Product(3L, "Soccer Ball"));
            add(new Product(4L, "Golf Clubs Set"));
            add(new Product(5L, "Volleyball"));
            add(new Product(6L, "Baseball Bat"));
            add(new Product(7L, "Badminton Racket"));
            add(new Product(8L, "Table Tennis Paddle"));
            add(new Product(9L, "Hockey Stick"));
            add(new Product(10L, "Cricket Bat"));
            add(new Product(11L, "Frisbee"));
            add(new Product(12L, "Skateboard"));
            add(new Product(13L, "Roller Skates"));
            add(new Product(14L, "Ice Skates"));
            add(new Product(15L, "Snowboard"));
            add(new Product(16L, "Ski Set"));
            add(new Product(17L, "Swimming Goggles"));
            add(new Product(18L, "Diving Mask"));
            add(new Product(19L, "Snorkel"));
            add(new Product(20L, "Surfboard"));
            add(new Product(21L, "Kayak"));
            add(new Product(22L, "Canoe"));
            add(new Product(23L, "Rowing Oars"));
            add(new Product(24L, "Fishing Rod"));
            add(new Product(25L, "Climbing Harness"));
            add(new Product(26L, "Climbing Shoes"));
            add(new Product(27L, "Climbing Rope"));
            add(new Product(28L, "Boxing Gloves"));
            add(new Product(29L, "Punching Bag"));
            add(new Product(30L, "Yoga Mat"));
            add(new Product(31L, "Dumbbell Set"));
            add(new Product(32L, "Kettlebell"));
            add(new Product(33L, "Jump Rope"));
            add(new Product(34L, "Resistance Bands"));
            add(new Product(35L, "Foam Roller"));
            add(new Product(36L, "Exercise Ball"));
            add(new Product(37L, "Treadmill"));
            add(new Product(38L, "Stationary Bike"));
            add(new Product(39L, "Elliptical Machine"));
            add(new Product(40L, "Rowing Machine"));
            add(new Product(41L, "Weight Bench"));
            add(new Product(42L, "Barbell"));
            add(new Product(43L, "Weight Plates"));
            add(new Product(44L, "Pull-up Bar"));
            add(new Product(45L, "Gymnastics Rings"));
            add(new Product(46L, "Balance Beam"));
            add(new Product(47L, "Parallel Bars"));
            add(new Product(48L, "Pommel Horse"));
            add(new Product(49L, "Trampoline"));
            add(new Product(50L, "Archery Bow"));
            add(new Product(51L, "Archery Arrows"));
            add(new Product(52L, "Fencing Sword"));
            add(new Product(53L, "Fencing Mask"));
            add(new Product(54L, "Javelin"));
            add(new Product(55L, "Discus"));
            add(new Product(56L, "Shot Put"));
            add(new Product(57L, "Pole Vault Pole"));
            add(new Product(58L, "High Jump Bar"));
            add(new Product(59L, "Starting Blocks"));
            add(new Product(60L, "Hurdles"));
            add(new Product(61L, "Stopwatch"));
            add(new Product(62L, "Referee Whistle"));
            add(new Product(63L, "Scoreboard"));
            add(new Product(64L, "Team Jersey"));
            add(new Product(65L, "Sports Shorts"));
            add(new Product(66L, "Running Shoes"));
            add(new Product(67L, "Cleats"));
            add(new Product(68L, "Swim Cap"));
            add(new Product(69L, "Swim Fins"));
            add(new Product(70L, "Wetsuit"));
            add(new Product(71L, "Lifejacket"));
            add(new Product(72L, "Bicycle Helmet"));
            add(new Product(73L, "Knee Pads"));
            add(new Product(74L, "Elbow Pads"));
            add(new Product(75L, "Wrist Guards"));
            add(new Product(76L, "Mouth Guard"));
            add(new Product(77L, "Shin Guards"));
            add(new Product(78L, "Golf Balls"));
            add(new Product(79L, "Tennis Balls"));
            add(new Product(80L, "Shuttlecocks"));
            add(new Product(81L, "Table Tennis Balls"));
            add(new Product(82L, "Baseball"));
            add(new Product(83L, "Softball"));
            add(new Product(84L, "Cricket Ball"));
            add(new Product(85L, "Squash Ball"));
            add(new Product(86L, "Racquetball"));
            add(new Product(87L, "Lacrosse Stick"));
            add(new Product(88L, "Lacrosse Ball"));
            add(new Product(89L, "Rugby Ball"));
            add(new Product(90L, "American Football"));
            add(new Product(91L, "Handball"));
            add(new Product(92L, "Water Polo Ball"));
            add(new Product(93L, "Ping Pong Table"));
            add(new Product(94L, "Billiard Cue"));
            add(new Product(95L, "Billiard Balls"));
            add(new Product(96L, "Dartboard"));
            add(new Product(97L, "Darts"));
            add(new Product(98L, "Chess Set"));
            add(new Product(99L, "Poker Chips"));
            add(new Product(100L, "Playing Cards"));
        }};
        return products;
    }
}
