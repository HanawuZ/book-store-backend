using Microsoft.EntityFrameworkCore;
using UserService.Configs.Databases;
using System.Text.Json;
using UserService.Apps.Users.Services;
using UserService.Apps.Users.Repository;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using UserService.Libs.Security;
using UserService.Configs.Middlewares;
using UserService.Apps.CustomerAddresses.Repository;
using UserService.Apps.CustomerAddresses.Services;
using UserService.Apps.HealthCheck.PingPong;


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
builder.Services.AddGrpc();
builder.Services.AddGrpcReflection();

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
builder.Services.AddScoped<ICustomerAddressService, ConcretedCustomerAddressService>();
builder.Services.AddScoped<ICustomerAddressRepository, ConcretedCustomerAddressRepository>();
builder.Services.AddScoped<IJwtUtility, JwtUtility>();
builder.Services
    .AddAuthentication(options =>
    {
        options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
        options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
    })
    .AddJwtBearer(JwtBearerMiddleware.ConfigureJwtBearerOptions);

//builder.WebHost.ConfigureKestrel(options =>
//{
//    // gRPC endpoint
//    options.Listen(IPAddress.Any, 5005, listenOptions =>
//    {
//        listenOptions.Protocols = HttpProtocols.Http2; // Force HTTP/2 for gRPC
//    });
//});

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
    app.MapGrpcReflectionService();
}

app.UseHttpsRedirection();
app.UseCors("MyAllowSpecificOrigins");
app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();
app.MapGrpcService<PingPongGrpcServerImpl>();

app.Run();
