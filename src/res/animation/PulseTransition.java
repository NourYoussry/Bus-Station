package res.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;


public class PulseTransition extends CachedTimelineTransition {
    public PulseTransition(final Node node) {
        super(
                node,
                TimelineBuilder.create()
                        .keyFrames(
                                new KeyFrame(Duration.millis(0),
                                        new KeyValue(node.scaleXProperty(), 1, WEB_EASE),
                                        new KeyValue(node.scaleYProperty(), 1, WEB_EASE)
                                ),
                                new KeyFrame(Duration.millis(100),
                                        new KeyValue(node.scaleXProperty(), 1.1, WEB_EASE),
                                        new KeyValue(node.scaleYProperty(), 1.1, WEB_EASE)
                                ),
                                new KeyFrame(Duration.millis(200),
                                        new KeyValue(node.scaleXProperty(), 1, WEB_EASE),
                                        new KeyValue(node.scaleYProperty(), 1, WEB_EASE)
                                )
                        )
                        .build()
        );
        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
    }
}
