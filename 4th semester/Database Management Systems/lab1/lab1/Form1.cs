using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace lab1
{
    public partial class Form1 : Form
    {
        SqlConnection connection;
        SqlDataAdapter daReservations, daCustomer;
        DataSet ds;
        SqlCommandBuilder cb;
        BindingSource bsReservations, bsCustomer;
        public Form1()
        {
            InitializeComponent();
        }


        private void button1_Click(object sender, EventArgs e)
        {
            daReservations.Update(ds, "Reservations");
        }

        private void button2_Click(object sender, EventArgs e)
        {
            connection = new SqlConnection("Data Source=DESKTOP-1BLU5QN\\SQLEXPRESS;Initial Catalog=Cats Cafe;Integrated Security=True");
            ds = new DataSet();

            daReservations = new SqlDataAdapter("select * from Reservations", connection);
            daCustomer = new SqlDataAdapter("select * from Customer", connection);
            cb = new SqlCommandBuilder(daReservations);

            daReservations.Fill(ds, "Reservations");
            daCustomer.Fill(ds, "Customer");

            DataRelation dr = new DataRelation("FK_Customer_Reservations",
            ds.Tables["Customer"].Columns["customerID"], ds.Tables["Reservations"].Columns["customerID"]);
            ds.Relations.Add(dr);

            bsReservations = new BindingSource();
            bsCustomer = new BindingSource();

            bsCustomer.DataSource = ds;
            bsCustomer.DataMember = "Customer";

            bsReservations.DataSource = bsCustomer;
            bsReservations.DataMember = "FK_Customer_Reservations";
            
            gridCustomer.DataSource = bsReservations;
            gridReservations.DataSource = bsCustomer;
        }

    }
}
