import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class GameManager {

    private int hearts = 3;
    private int distance = 0;

    private int record = 0;

    private int finishDistance = 1000;

    private int speed = 3;

    private boolean gameOver;
    private boolean finished;

    private int roadOffset;

    public GameManager() {
        loadRecord();
    }

    public void update() {

        roadOffset += speed;

        if (roadOffset >= 25) {
            roadOffset = 0;
        }

        distance++;

        if (distance > record) {
            record = distance;
        }

        if (distance >= finishDistance) {
            finished = true;
        }
    }

    public void addDistance(int amount) {
        distance += amount;

        if (distance > record) {
            record = distance;
        }
    }

    public void loseHeart() {

        if (hearts > 0) {
            hearts--;
        }

        if (hearts <= 0) {
            gameOver = true;
        }
    }

    public int getHearts() {
        return hearts;
    }

    public int getDistance() {
        return distance;
    }

    public int getRecord() {
        return record;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRoadOffset() {
        return roadOffset;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isFinished() {
        return finished;
    }

    public void saveRecord() {

        RecordStore store = null;

        try {

            store = RecordStore.openRecordStore(
                "CarRacingRecord",
                true
            );

            byte[] data = new byte[4];

            data[0] = (byte)(record >> 24);
            data[1] = (byte)(record >> 16);
            data[2] = (byte)(record >> 8);
            data[3] = (byte)record;

            if (store.getNumRecords() == 0) {
                store.addRecord(
                    data,
                    0,
                    data.length
                );
            } else {
                store.setRecord(
                    1,
                    data,
                    0,
                    data.length
                );
            }

        } catch (RecordStoreException e) {
            // Ignore save errors on unsupported devices.
        } finally {

            if (store != null) {
                try {
                    store.closeRecordStore();
                } catch (Exception e) {
                }
            }
        }
    }

    private void loadRecord() {

        RecordStore store = null;

        try {

            store = RecordStore.openRecordStore(
                "CarRacingRecord",
                true
            );

            if (store.getNumRecords() > 0) {

                byte[] data = store.getRecord(1);

                if (data != null && data.length >= 4) {

                    record =
                        ((data[0] & 0xFF) << 24) |
                        ((data[1] & 0xFF) << 16) |
                        ((data[2] & 0xFF) << 8) |
                        (data[3] & 0xFF);
                }
            }

        } catch (Exception e) {
            record = 0;
        } finally {

            if (store != null) {
                try {
                    store.closeRecordStore();
                } catch (Exception e) {
                }
            }
        }
    }
}