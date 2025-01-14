﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;
using UserService.Configs.Databases;

#nullable disable

namespace UserService.Migrations
{
    [DbContext(typeof(PostgresDatabaseContext))]
    [Migration("20250114142125_Migrate140120252106")]
    partial class Migrate140120252106
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "9.0.0")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            NpgsqlModelBuilderExtensions.UseIdentityByDefaultColumns(modelBuilder);

            modelBuilder.Entity("UserService.Models.Entities.Customer", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("text")
                        .HasColumnName("id");

                    b.Property<string>("CreatedBy")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("created_by");

                    b.Property<DateTime>("CreatedDate")
                        .HasColumnType("timestamp without time zone")
                        .HasColumnName("created_date");

                    b.Property<string>("FirstName")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("firstname");

                    b.Property<string>("LastName")
                        .HasColumnType("text")
                        .HasColumnName("lastname");

                    b.Property<string>("PhoneOne")
                        .HasColumnType("text")
                        .HasColumnName("phone_one");

                    b.Property<string>("PhoneTwo")
                        .HasColumnType("text")
                        .HasColumnName("phone_two");

                    b.Property<string>("UpdatedBy")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("updated_by");

                    b.Property<DateTime>("UpdatedDate")
                        .HasColumnType("timestamp without time zone")
                        .HasColumnName("updated_date");

                    b.HasKey("Id")
                        .HasName("pk_customers");

                    b.ToTable("customers", (string)null);
                });

            modelBuilder.Entity("UserService.Models.Entities.User", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("text")
                        .HasColumnName("id");

                    b.Property<bool>("AccountNonExpired")
                        .HasColumnType("boolean")
                        .HasColumnName("account_non_expired");

                    b.Property<bool>("AccountNonLocked")
                        .HasColumnType("boolean")
                        .HasColumnName("account_non_locked");

                    b.Property<string>("CreatedBy")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("created_by");

                    b.Property<DateTime>("CreatedDate")
                        .HasColumnType("timestamp without time zone")
                        .HasColumnName("created_date");

                    b.Property<bool>("CredentialsNonExpired")
                        .HasColumnType("boolean")
                        .HasColumnName("credentials_non_expired");

                    b.Property<string>("Email")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("email");

                    b.Property<bool>("IsActive")
                        .HasColumnType("boolean")
                        .HasColumnName("is_active");

                    b.Property<bool>("IsUsing2FA")
                        .HasColumnType("boolean")
                        .HasColumnName("is_using_2fa");

                    b.Property<string>("Password")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("password");

                    b.Property<string>("ProfilePicture")
                        .HasColumnType("text")
                        .HasColumnName("profile_picture");

                    b.Property<string>("Provider")
                        .HasColumnType("text")
                        .HasColumnName("provider");

                    b.Property<string>("ProviderId")
                        .HasColumnType("text")
                        .HasColumnName("provider_id");

                    b.Property<string>("Secret")
                        .HasColumnType("text")
                        .HasColumnName("secret");

                    b.Property<string>("UpdatedBy")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("updated_by");

                    b.Property<DateTime>("UpdatedDate")
                        .HasColumnType("timestamp without time zone")
                        .HasColumnName("updated_date");

                    b.Property<string>("Username")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("username");

                    b.HasKey("Id")
                        .HasName("pk_users");

                    b.ToTable("users", (string)null);
                });

            modelBuilder.Entity("UserService.Models.Entities.UserMapping", b =>
                {
                    b.Property<string>("Id")
                        .HasColumnType("text")
                        .HasColumnName("id");

                    b.Property<string>("CustomerId")
                        .HasColumnType("text")
                        .HasColumnName("customer_id");

                    b.Property<string>("UserId")
                        .IsRequired()
                        .HasColumnType("text")
                        .HasColumnName("user_id");

                    b.HasKey("Id")
                        .HasName("pk_user_mappings");

                    b.ToTable("user_mappings", (string)null);
                });
#pragma warning restore 612, 618
        }
    }
}