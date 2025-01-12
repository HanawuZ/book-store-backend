using Microsoft.EntityFrameworkCore;
using UserService.Configs.Databases;
using UserService.Models.Entities.Seeds;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Logging.ClearProviders();
builder.Logging.AddConsole();
builder.Services.AddLogging();

builder.Services.AddDbContext<PostgresDatabaseContext>(options =>
    options
        .UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection"))
        .UseSnakeCaseNamingConvention()
);

builder.Services.AddCors(options =>
{
    options.AddPolicy(
       name: "MyAllowSpecificOrigins",
       policy =>
       {
           policy.WithOrigins("http://localhost:5045");       
       });
});

var app = builder.Build();

// Test database connection during startup
using (var scope = app.Services.CreateScope())
{
    var dbContext = scope.ServiceProvider.GetRequiredService<PostgresDatabaseContext>();

    try
    {
        // Attempt to connect to the database
        dbContext.Database.OpenConnection();
        UserSeeder.Seed(dbContext);
        Console.WriteLine("Database connection successful and data seeded!");
    }
    catch (Exception ex)
    {
        Console.WriteLine($"Database connection failed: {ex.Message}");
    } 
    finally
    {
        dbContext.Database.CloseConnection();
    }
}

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseCors("MyAllowSpecificOrigins");

app.UseAuthorization();

app.MapControllers();

app.Run();
