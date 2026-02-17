/*
Base de datos para taqueria bigotes
*/
CREATE DATABASE TaqueriaBD;
GO

USE TaqueriaBD;
GO

CREATE TABLE Producto (
    idProducto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    descripcion VARCHAR(255),
    urlFoto NVARCHAR(255),
    activo BIT NOT NULL DEFAULT 1
);


CREATE TABLE Insumo (
    idInsumo INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    unidadMedida VARCHAR(50) NOT NULL
);


CREATE TABLE Empleado (
    idEmpleado INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    puesto VARCHAR(50) NOT NULL,
    activo BIT NOT NULL DEFAULT 1
);


CREATE TABLE Usuario (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    id_empleado INT NOT NULL,
    CONSTRAINT FK_Usuario_Empleado
        FOREIGN KEY (id_empleado) REFERENCES Empleado(idEmpleado)
);


CREATE TABLE Inventario (
    idInventario INT IDENTITY(1,1) PRIMARY KEY,
    idInsumo INT NOT NULL,
    cantidadDisponible DECIMAL(10,2) NOT NULL,
    fechaActualizacion DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT FK_Inventario_Insumo FOREIGN KEY (idInsumo) REFERENCES Insumo(idInsumo)
);


CREATE TABLE Pedido (
    idPedido INT IDENTITY(1,1) PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT GETDATE(),
    total DECIMAL(10,2) NOT NULL,
    idEmpleado INT NOT NULL,
    CONSTRAINT FK_Pedido_Empleado FOREIGN KEY (idEmpleado) REFERENCES Empleado(idEmpleado)
);


CREATE TABLE DetallePedido (
    idDetalle INT IDENTITY(1,1) PRIMARY KEY,
    idPedido INT NOT NULL,
    idProducto INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    CONSTRAINT FK_DetallePedido_Pedido FOREIGN KEY (idPedido) REFERENCES Pedido(idPedido),
    CONSTRAINT FK_DetallePedido_Producto FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
);


CREATE TABLE productoInsumo (
    idProducto INT NOT NULL,
    idInsumo INT NOT NULL,
    cantidadUsada DECIMAL(10,2) NOT NULL,
    CONSTRAINT PK_productoInsumo PRIMARY KEY (idProducto, idInsumo),
    CONSTRAINT FK_productoInsumo_Producto FOREIGN KEY (idProducto) REFERENCES Producto(idProducto),
    CONSTRAINT FK_productoInsumo_Insumo FOREIGN KEY (idInsumo) REFERENCES Insumo(idInsumo)
);




