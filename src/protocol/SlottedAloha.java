package protocol;

import java.util.Random;

/**
 * A fairly trivial Medium Access Control scheme.
 * @author Jaco ter Braak, Twente University
 * @version 05-12-2013
 */
public class SlottedAloha implements IMACProtocol {

    @Override
    public TransmissionInfo TimeslotAvailable(MediumState previousMediumState,
                                              int controlInformation, int localQueueLength) {
        // No data to send, just be quiet
        if (localQueueLength == 0) {
            System.out.println("SLOT - No data to send.");
            return new TransmissionInfo(TransmissionType.Silent, 0);
        }

        // Randomly transmit with 25% probability
        if (new Random().nextInt(100) < 25) {
            System.out.println("SLOT - Sending data and hope for no collision.");
            return new TransmissionInfo(TransmissionType.Data, 0);
        } else {
            System.out.println("SLOT - Not sending data to give room for others.");
            return new TransmissionInfo(TransmissionType.Silent, 0);
        }

    }

}
