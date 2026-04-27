package cz.uhk.timetable.gui;

import cz.uhk.timetable.model.LocationRoom;
import cz.uhk.timetable.model.LocationTimetable;
import cz.uhk.timetable.utils.ITimetableProvider;
import cz.uhk.timetable.utils.StagRoomProvider;
import cz.uhk.timetable.utils.StagTimetableProvider;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimetableFrame extends JFrame {
    private ITimetableProvider timetableProvider = new StagTimetableProvider();
    private LocationTimetable timetable;
    private JTable tableTimetable;
    private TimetableModel timetableModel;
    private LocationRoom locationRoom;
    private StagRoomProvider stagRoomProvider = new StagRoomProvider();

    public TimetableFrame() {
        super("Location Timetable");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGui();
    }

    private void initGui() {
        timetableModel = new TimetableModel();
        tableTimetable = new JTable(timetableModel);
        add(new JScrollPane(tableTimetable), BorderLayout.CENTER);
        add(createToolBar(), BorderLayout.NORTH);
        pack();
    }

    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();

        JComboBox<String> comboBoxBuilding = new JComboBox<>();
        comboBoxBuilding.setModel(new DefaultComboBoxModel<>(new String[]{"A", "B", "C", "CX", "E", "F", "H", "J", "P", "S"}));
        JComboBox<String> comboBoxRoom = new JComboBox<>();
        comboBoxBuilding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                locationRoom = stagRoomProvider.readRooms(comboBoxBuilding.getSelectedItem().toString());
                comboBoxRoom.setModel(new DefaultComboBoxModel<>(locationRoom.getRoomNames()));
            }
        });

        Action updateTimetable = new AbstractAction("Update Timetable") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    timetable = timetableProvider.readTimetable(comboBoxBuilding.getSelectedItem().toString(), comboBoxRoom.getSelectedItem().toString());
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(
                            TimetableFrame.this,
                            String.format("Nebyla vybrána místnost."),
                            "Chyba", JOptionPane.ERROR_MESSAGE);
                }
                timetableModel.fireTableDataChanged();
            }
        };

        toolBar.add(comboBoxBuilding);
        toolBar.add(comboBoxRoom);
        toolBar.add(updateTimetable);
        return toolBar;
    }

    class TimetableModel extends AbstractTableModel {

        private static final String[] COLNAMES = {"ZKRATKA", "NÁZEV", "UČITEL", "TYP", "DEN", "ZAČÁTEK", "KONEC"};

        @Override
        public int getRowCount() {
            try {
                return timetable.getActivities().size();
            } catch (Exception e) {
                return 0;
            }
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public String getColumnName(int column) {
            return COLNAMES[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var a = timetable.getActivities().get(rowIndex);
            return switch (columnIndex) {
                case 0 -> a.getId();
                case 1 -> a.getName();
                case 2 -> a.getTeacher();
                case 3 -> a.getType();
                case 4 -> a.getDay();
                case 5 -> a.getStart();
                case 6 -> a.getEnd();
                default -> "";
            };
        }
    }
}