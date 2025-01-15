using Microsoft.EntityFrameworkCore;
using UserService.Configs.Databases;
using System.Text.Json;
using UserService.Apps.Users.Services;
using UserService.Apps.Users.Repository;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers().
    AddJsonOptions(options =>
    {
        options.JsonSerializerOptions.PropertyNamingPolicy = JsonNamingPolicy.CamelCase;
    });
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

builder.Services.AddScoped<IUserService, ConcretedUserService>();
builder.Services.AddScoped<IUserRepository, ConcretedUserRepository>();
builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuer = true,
            ValidateAudience = true,
            ValidateLifetime = true,
            ValidateIssuerSigningKey = true,
            ValidIssuer = "example.com",
            ValidAudience = "example.com",
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"))
        };
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
        //UserSeeder.Seed(dbContext);
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

app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

app.Run();
