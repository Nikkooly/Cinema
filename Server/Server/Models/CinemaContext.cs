using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Server.Models
{
    public partial class CinemaContext : DbContext
    {
        public CinemaContext()
        {
        }

        public CinemaContext(DbContextOptions<CinemaContext> options)
            : base(options)
        {
        }

        public virtual DbSet<CinemaInfo> CinemaInfo { get; set; }
        public virtual DbSet<FilmInfo> FilmInfo { get; set; }
        public virtual DbSet<HallInfo> HallInfo { get; set; }
        public virtual DbSet<Rating> Rating { get; set; }
        public virtual DbSet<Roles> Roles { get; set; }
        public virtual DbSet<Seance> Seance { get; set; }
        public virtual DbSet<Tickets> Tickets { get; set; }
        public virtual DbSet<UserData> UserData { get; set; }
        public virtual DbSet<ViewFilmInfo> ViewFilmInfo { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseSqlServer("Server=DESKTOP-15P21ID;Initial Catalog=Cinema;user=admin;password=Nick1063;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<CinemaInfo>(entity =>
            {
                entity.ToTable("cinema_info");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .ValueGeneratedNever();

                entity.Property(e => e.Adress)
                    .IsRequired()
                    .HasColumnName("adress");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnName("name")
                    .HasMaxLength(50);
            });

            modelBuilder.Entity<FilmInfo>(entity =>
            {
                entity.ToTable("film_info");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .ValueGeneratedNever();

                entity.Property(e => e.Country)
                    .IsRequired()
                    .HasColumnName("country");

                entity.Property(e => e.Description)
                    .HasColumnName("description")
                    .HasColumnType("text");

                entity.Property(e => e.Duration).HasColumnName("duration");

                entity.Property(e => e.Genre)
                    .IsRequired()
                    .HasColumnName("genre");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnName("name");

                entity.Property(e => e.Poster)
                    .IsRequired()
                    .HasColumnName("poster");

                entity.Property(e => e.Year).HasColumnName("year");
            });

            modelBuilder.Entity<HallInfo>(entity =>
            {
                entity.ToTable("hall_info");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .ValueGeneratedNever();

                entity.Property(e => e.CinemaId).HasColumnName("cinema_id");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnName("name")
                    .HasMaxLength(50);

                entity.Property(e => e.Places).HasColumnName("places");

                entity.HasOne(d => d.Cinema)
                    .WithMany(p => p.HallInfo)
                    .HasForeignKey(d => d.CinemaId)
                    .HasConstraintName("Fk_HallInfo_Cascade");
            });

            modelBuilder.Entity<Rating>(entity =>
            {
                entity.ToTable("rating");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasDefaultValueSql("(newid())");

                entity.Property(e => e.FilmId).HasColumnName("film_id");

                entity.Property(e => e.Rating1).HasColumnName("rating");

                entity.Property(e => e.Review)
                    .IsRequired()
                    .HasColumnName("review");

                entity.Property(e => e.UserId).HasColumnName("user_id");

                entity.HasOne(d => d.Film)
                    .WithMany(p => p.Rating)
                    .HasForeignKey(d => d.FilmId)
                    .HasConstraintName("Fk_Rating_Film_id__Cascade");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Rating)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("Fk_Rating_User_id__Cascade");
            });

            modelBuilder.Entity<Roles>(entity =>
            {
                entity.ToTable("roles");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Role).HasColumnName("role");
            });

            modelBuilder.Entity<Seance>(entity =>
            {
                entity.ToTable("seance");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .ValueGeneratedNever();

                entity.Property(e => e.EndTime)
                    .HasColumnName("end_time")
                    .HasColumnType("smalldatetime");

                entity.Property(e => e.FilmId).HasColumnName("film_id");

                entity.Property(e => e.HallId).HasColumnName("hall_id");

                entity.Property(e => e.StartTime)
                    .HasColumnName("start_time")
                    .HasColumnType("smalldatetime");

                entity.Property(e => e.TicketPrice).HasColumnName("ticket_price");

                entity.HasOne(d => d.Film)
                    .WithMany(p => p.Seance)
                    .HasForeignKey(d => d.FilmId)
                    .HasConstraintName("Fk_Seance_Film_id__Cascade");

                entity.HasOne(d => d.Hall)
                    .WithMany(p => p.Seance)
                    .HasForeignKey(d => d.HallId)
                    .HasConstraintName("Fk_Seance_Hall_id___Cascade");
            });

            modelBuilder.Entity<Tickets>(entity =>
            {
                entity.ToTable("tickets");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .ValueGeneratedNever();

                entity.Property(e => e.Place).HasColumnName("place");

                entity.Property(e => e.SeanceId).HasColumnName("seance_id");

                entity.Property(e => e.UserId).HasColumnName("user_id");

                entity.HasOne(d => d.Seance)
                    .WithMany(p => p.Tickets)
                    .HasForeignKey(d => d.SeanceId)
                    .HasConstraintName("Fk_Ticket_Seance_id_Cascade");

                entity.HasOne(d => d.User)
                    .WithMany(p => p.Tickets)
                    .HasForeignKey(d => d.UserId)
                    .HasConstraintName("Fk_Ticket_id_user_Cascade");
            });

            modelBuilder.Entity<UserData>(entity =>
            {
                entity.ToTable("user_data");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasDefaultValueSql("(newid())");

                entity.Property(e => e.Email)
                    .IsRequired()
                    .HasColumnName("email");

                entity.Property(e => e.Login)
                    .IsRequired()
                    .HasColumnName("login")
                    .HasMaxLength(50);

                entity.Property(e => e.Password)
                    .IsRequired()
                    .HasColumnName("password")
                    .HasMaxLength(50);

                entity.Property(e => e.RoleId).HasColumnName("role_id");

                entity.HasOne(d => d.Role)
                    .WithMany(p => p.UserData)
                    .HasForeignKey(d => d.RoleId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK__user_data__role___145C0A3F");
            });

            modelBuilder.Entity<ViewFilmInfo>(entity =>
            {
                entity.HasNoKey();

                entity.ToView("View_FilmInfo");

                entity.Property(e => e.Country).HasColumnName("country");

                entity.Property(e => e.Description)
                    .HasColumnName("description")
                    .HasColumnType("text");

                entity.Property(e => e.Duration).HasColumnName("duration");

                entity.Property(e => e.Genre).HasColumnName("genre");

                entity.Property(e => e.Id).HasColumnName("id");

                entity.Property(e => e.Name).HasColumnName("name");

                entity.Property(e => e.Poster).HasColumnName("poster");

                entity.Property(e => e.Rating).HasColumnName("rating");

                entity.Property(e => e.Year).HasColumnName("year");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
