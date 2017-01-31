/**
 * Created by DT173 on 2026/10/8.
 */
import groovy.sql.Sql
String name = "root";
String pass = "Elekchina123!";
def sql = groovy.sql.Sql.newInstance("jdbc:mysql://localhost:3306/choiceres",
        name, pass, "com.mysql.jdbc.Driver");
columnDswitch_Code = sql.rows('SELECT dswitch_code from reservation_mapping');
columnDswitch_Reservation_Id = sql.rows('SELECT dswitch_reservation_id from split_reservation_mapping');
columnDswitch_Split_Reservation_Id = sql.rows('SELECT split_reservation_id from split_reservation_mapping');

if (columnDswitch_Code.size()==1)
{
    if (columnDswitch_Code[0][0] == columnDswitch_Reservation_Id[0][0])
        if(columnDswitch_Code[0][0] == columnDswitch_Split_Reservation_Id[0][0])
           return true;
    else
            return false;
}
else
{
    String reservationID=  columnDswitch_Reservation_Id[0][0].toString();
    for(int i = 1; i <columnDswitch_Reservation_Id.size();i++)
    {
        if(reservationID != columnDswitch_Reservation_Id[i][0])
            return false;
    }
    for(int i = 0; i<columnDswitch_Code.size();i++)
    {
        String t_a = columnDswitch_Code[i][0].toString();
        String t_b = columnDswitch_Split_Reservation_Id[i][0].toString();
        if(t_a!=t_b)
            return false

    }
    return true;

}

