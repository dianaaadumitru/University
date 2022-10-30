import domain.Vector;
import threads.ConsumerThread;
import threads.ProducerConsumerBuffer;
import threads.ProducerThread;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Vector vec1 = new Vector(Arrays.asList(1, 2, 3, 4));
        Vector vec2 = new Vector(Arrays.asList(4, 3, 2, 1));

        ProducerConsumerBuffer buffer = new ProducerConsumerBuffer();

        ProducerThread producer = new ProducerThread(vec1, vec2, buffer);
        ConsumerThread consumer = new ConsumerThread(vec1.getLength(), buffer);

        producer.start();
        consumer.start();
    }
}
