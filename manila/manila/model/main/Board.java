package manila.model.main;

import manila.model.position.*;

import java.util.ArrayList;

/**
 * 马尼拉游戏面板
 */
public class Board {
    /**
     * 位置
     */
    Position[] positions;
    Boat[] boats;

    /**
     * 格数
     */
    private int gridNumber;

    /**
     * 小船构造函数
     *
     * @param gridNumber 格数，如13
     * @param positions  位置数组
     * @param boats      船的数组
     */
    public Board(int gridNumber, Position[] positions, Boat[] boats) {
        this.positions = positions;
        this.gridNumber = gridNumber;
        this.boats = boats;
    }

    public Position[] getPositions() {
        return positions;
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }

    public void setGridNumber(int gridNumber) {
        this.gridNumber = gridNumber;
    }

    public Boat[] getBoats() {
        return boats;
    }

    public void setBoats(Boat[] boats) {
        this.boats = boats;
    }

    public ArrayList<Port> getPorts() {
        ArrayList<Port> ports = new ArrayList<>();
        for (Position position : positions) {
            if (position instanceof Port) {
                ports.add((Port) position);
            }
        }

        return ports;
    }

    public Port getFirstSparePort() {
        for (Position position : positions) {
            if (position instanceof Port) {
                if ((position).isSpare()) {
                    return (Port) position;
                }
            }
        }

        return null;
    }

    public ArrayList<PirateBay> getPirateBays() {
        ArrayList<PirateBay> pirateBays = new ArrayList<>();
        for (Position position : positions) {
            if (position instanceof PirateBay) {
                pirateBays.add((PirateBay) position);
            }
        }
        return pirateBays;
    }

    public PirateBay getFirstSparePirateBay() {
        for (Position position : positions) {
            if (position instanceof PirateBay) {
                if (position.isSpare()) {
                    return (PirateBay) position;
                }
            }
        }

        return null;
    }

    public ArrayList<IslandOfNavigator> getIslandOfNavigators() {
        ArrayList<IslandOfNavigator> islandOfNavigators = new ArrayList<>();
        for (Position position : positions) {
            if (position instanceof IslandOfNavigator) {
                islandOfNavigators.add((IslandOfNavigator) position);
            }
        }
        return islandOfNavigators;
    }

    public IslandOfNavigator getFirstSpareIslandOfNavigator() {
        for (Position position : positions) {
            if (position instanceof IslandOfNavigator) {
                return (IslandOfNavigator) position;
            }
        }

        return null;
    }

    public ArrayList<Shipyard> getShipyards() {
        ArrayList<Shipyard> shipyards = new ArrayList<>();
        for (Position position : positions) {
            if (position instanceof Shipyard) {
                shipyards.add((Shipyard) position);
            }
        }
        return shipyards;
    }

    public Shipyard getFirstSpareShipyard() {
        for (Position position : positions) {
            if (position instanceof Shipyard) {
                return (Shipyard) position;
            }
        }

        return null;
    }

    public InsuranceCompany getInsuranceCompany() {
        for (Position position : positions) {
            if (position instanceof InsuranceCompany) {
                return (InsuranceCompany) position;
            }
        }

        return null;
    }


    public boolean ifExistPirate() {
        for (Position position : positions) {
            if (position instanceof PirateBay && !position.isSpare()) {
                return true;
            }
        }
        return false;
    }

    public boolean ifExistIslandOfNavigator() {
        for (Position position : positions) {
            if (position instanceof IslandOfNavigator && !position.isSpare()) {
                return true;
            }
        }
        return false;
    }


}
