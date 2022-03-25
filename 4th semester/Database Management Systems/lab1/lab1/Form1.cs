using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace lab1
{
    public partial class Form1 : Form
    {
        private SqlConnection connection;
        private DataSet ds;
        private SqlDataAdapter daParent, daChild;
        private SqlCommandBuilder cb;
        private BindingSource bsParent, bsChild;

        public Form1()
        {
            InitializeComponent();
        }

        private void loadData()
        {
            this.connection = new SqlConnection(getConnectionString());

            this.ds = new DataSet();

            //load parent table data into the dataset
            this.daParent = new SqlDataAdapter("SELECT * FROM Customer", connection);
            cb = new SqlCommandBuilder(daParent);
            this.daParent.Fill(ds, "Customer");

            //load child table data into the dataset
            this.daChild = new SqlDataAdapter("select * from Reservations", connection);
            cb = new SqlCommandBuilder(daChild);
            this.daChild.Fill(ds, "Reservations");

            //create a new relation representing the foreign key constraint of the 1:n relation
            DataRelation dr = new DataRelation("FK_Customer_Reservations",
            ds.Tables["Customer"].Columns["customerID"], ds.Tables["Reservations"].Columns["customerID"]);
            this.ds.Relations.Add(dr);

            //create the binding sources for the parent and child tables
            bsParent = new BindingSource();
            bsParent.DataSource = ds;
            bsParent.DataMember = "Customer";

            bsChild = new BindingSource();
            bsChild.DataSource = bsParent;
            bsChild.DataMember = "FK_Customer_Reservations";

            //fill the tableGridViews 
            this.viewParent.DataSource = bsParent;
            this.viewChildren.DataSource = bsChild;
        }


        private void updateDb_Click(object sender, EventArgs e)
        {
            //save the changes into the db
            this.daChild.Update(this.ds, "Reservations");
        }


        private void button2_Click(object sender, EventArgs e)
        {
            loadData();
        }

        private String getConnectionString()
        {
            return "Data Source=DESKTOP-1BLU5QN;Initial Catalog=Cats Cafe;Integrated Security=true";
        }
    }
}