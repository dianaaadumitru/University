
namespace lab1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.gridCustomer = new System.Windows.Forms.DataGridView();
            this.gridReservations = new System.Windows.Forms.DataGridView();
            this.button2 = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.gridCustomer)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridReservations)).BeginInit();
            this.SuspendLayout();
            // 
            // gridCustomer
            // 
            this.gridCustomer.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridCustomer.Location = new System.Drawing.Point(66, 45);
            this.gridCustomer.Name = "gridCustomer";
            this.gridCustomer.RowHeadersWidth = 62;
            this.gridCustomer.RowTemplate.Height = 28;
            this.gridCustomer.Size = new System.Drawing.Size(597, 276);
            this.gridCustomer.TabIndex = 0;
            // 
            // gridReservations
            // 
            this.gridReservations.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.gridReservations.Location = new System.Drawing.Point(66, 364);
            this.gridReservations.Name = "gridReservations";
            this.gridReservations.RowHeadersWidth = 62;
            this.gridReservations.RowTemplate.Height = 28;
            this.gridReservations.Size = new System.Drawing.Size(597, 275);
            this.gridReservations.TabIndex = 1;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(885, 159);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(146, 55);
            this.button2.TabIndex = 2;
            this.button2.Text = "Connect";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(885, 494);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(146, 56);
            this.button1.TabIndex = 3;
            this.button1.Text = "Update DB";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1152, 714);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.gridReservations);
            this.Controls.Add(this.gridCustomer);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.gridCustomer)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridReservations)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView gridCustomer;
        private System.Windows.Forms.DataGridView gridReservations;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button1;
    }
}

