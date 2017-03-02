package protocol;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by myrthe on 2-3-17.
 */
public class TurnTaking implements IMACProtocol {

    private int id = -1;
    private int timeslot = -1;
    private ArrayList<Integer> nodeIDs;
    private boolean idGiven = false;

        @Override
        public TransmissionInfo TimeslotAvailable(MediumState previousMediumState,
        int controlInformation, int localQueueLength) {

            if (timeslot == -1) {
                timeslot = 1;
            } else {
                timeslot++;
            }

            if(timeslot < 20 && previousMediumState == MediumState.Succes) {
                if (nodeIDs == null) {
                    nodeIDs = new ArrayList<>();
                }
                nodeIDs.add(controlInformation);
                if (controlInformation == id) {
                    idGiven = true;
                }
            }

            if (timeslot < 20 && new Random().nextInt(10) == 1 && !idGiven) {
                if (id == -1) {
                    id = new Random().nextInt(25665);
                }
                System.out.println("SLOT - Sending node id");
                return new TransmissionInfo(TransmissionType.NoData, id);
            }



            if (timeslot == 20 && nodeIDs.get(0) == id) {
                if (localQueueLength == 0) {
                    System.out.println("SLOT - No data to send, passing on token");
                    return new TransmissionInfo(TransmissionType.NoData, nodeIDs.get(1));
                } else {
                    System.out.println("SLOT - Sending data, passing on token");
                    return new TransmissionInfo(TransmissionType.Data, nodeIDs.get(1));
                }
            }

            if (timeslot > 20 && controlInformation == id) {
                if (localQueueLength == 0) {
                    System.out.println("SLOT - No data to send, passing on token");
                    return new TransmissionInfo(TransmissionType.NoData, nodeIDs.get((timeslot - 19) % nodeIDs.size()));
                } else {
                    System.out.println("SLOT - Sending data, passing on token");
                    return new TransmissionInfo(TransmissionType.Data, nodeIDs.get((timeslot - 19) % nodeIDs.size()));
                }
            } else {
                System.out.println("SLOT - Token not in possession");
                return new TransmissionInfo(TransmissionType.Silent, 0);
            }



        }

    }
