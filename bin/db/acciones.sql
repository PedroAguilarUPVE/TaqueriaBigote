--PRODUCTOS
--Para insertar producto disponible
INSERT INTO Producto (nombre, precio, descripcion, urlFoto activo)
VALUES (@nombre, @precio, @descripcion, @foto, 1);

--Editar producto existente
UPDATE Producto
SET nombre = @nombre,
    precio = @precio,
    descripcion = @descripcion,
    urlFoto= @foto,
    activo = @activo
WHERE idProducto = @idProducto;

--Mostrar productos existentes/activos
SELECT 
    idProducto,
    nombre,
    precio,
    descripcion,
    urlFoto
FROM Producto
WHERE activo = 1
ORDER BY nombre;


--Eliminar producto activarlo o desactivarlo del menu
UPDATE Producto
SET activo = 0
WHERE idProducto = @idProducto;

--Eliminarlo totalmente 
DELETE Producto 
WHERE idProducto = @idProducto

--EMPLEADOS
--Insertar empleado
INSERT INTO Empleado (nombre, puesto, activo)
VALUES (@nombre, @puesto, 1);

--Editar empleado
UPDATE Empleado
SET nombre = @nombre,
    puesto = @puesto,
    activo = @activo
WHERE idEmpleado = @idEmpleado;

--Mostrar empleados
SELECT *
FROM Empleado
WHERE activo = 1;


--Eliminarlo de la bd 
DELETE Empleado 
WHERE idEmpleado = @idEmpleado;

--USUARIOS
--Insertar usuario
INSERT INTO Usuario (usuario, password, rol, id_empleado)
VALUES (@usuario, @password, @rol, @idEmpleado);

--Editar usuario
UPDATE Usuario
SET usuario = @usuario,
    password = @password,
    rol = @rol
WHERE id_usuario = @idUsuario;

--Mostrar usuarios
SELECT u.id_usuario, u.usuario, u.rol, e.nombre
FROM Usuario u
JOIN Empleado e ON u.id_empleado = e.idEmpleado;

--Eliminar usuario
DELETE FROM Usuario
WHERE id_usuario = @idUsuario;


--INSUMOS
--Insertar insumo
INSERT INTO Insumo (nombre, unidadMedida)
VALUES (@nombre, @unidadMedida);

--Editar insumo
UPDATE Insumo
SET nombre = @nombre,
    unidadMedida = @unidadMedida
WHERE idInsumo = @idInsumo;

--Mostrar insumos añadidos
SELECT * FROM Insumo;

--Eliminar insumo
DELETE FROM Insumo
WHERE idInsumo = @idInsumo;

--INVENTARIO
--Insertar inventario
INSERT INTO Inventario (idInsumo, cantidadDisponible)
VALUES (@idInsumo, @cantidad);

--Actualizar inventario
UPDATE Inventario
SET cantidadDisponible = @cantidad,
    fechaActualizacion = GETDATE() --El dia 
WHERE idInsumo = @idInsumo;

--Mostrar inventario actual
SELECT i.nombre, inv.cantidadDisponible, i.unidadMedida
FROM Inventario inv
JOIN Insumo i ON inv.idInsumo = i.idInsumo;

--Mostrar cantidad disponible solo de un insumo en especifico
SELECT i.nombre, inv.cantidadDisponible
FROM Inventario inv
WHERE  idInsumo=@idInsumo;
INNER JOIN Insumo i ON inv.idInsumo= i.idInsumo;

--Eliminar inventario
DELETE FROM Inventario
WHERE idInsumo = @idInsumo;

--PEDIDOS
--Insertar pedido
INSERT INTO Pedido (fecha, total, idEmpleado)
VALUES (GETDATE(), @total, @idEmpleado);

--DETALLE DEL PEDIDO
--Insertar detalle
INSERT INTO DetallePedido (idPedido, idProducto, cantidad, subtotal)
VALUES (@idPedido, @idProducto, @cantidad, @subtotal);

--Eliminar detalle
DELETE FROM DetallePedido
WHERE idDetalle = @idDetalle;
--Eliminar pedido
DELETE FROM Pedido
WHERE idPedido = @idPedido;

--Porcedimiento almacenado si se desea editar algun pedido 
CREATE PROCEDURE sp_EditarPedido
    @idPedido INT,
    @idProducto INT,
    @nuevaCantidad INT
AS
BEGIN
    -- Actualizar detalle
    UPDATE DetallePedido
    SET 
        cantidad = @nuevaCantidad,
        subtotal = @nuevaCantidad * (
            SELECT precio 
            FROM Producto 
            WHERE idProducto = @idProducto
        )
    WHERE idPedido = @idPedido
      AND idProducto = @idProducto;

    -- Recalcular total
    UPDATE Pedido
    SET total = (
        SELECT SUM(subtotal)
        FROM DetallePedido
        WHERE idPedido = @idPedido
    )
    WHERE idPedido = @idPedido;
END;

--Mostrar pedido completo en especifico
SELECT 
    p.idPedido,
    p.fecha,
    p.total AS totalPedido,
    pr.idProducto,
    pr.nombre AS producto,
    d.cantidad,
    d.subtotal
FROM Pedido p
JOIN DetallePedido d ON p.idPedido = d.idPedido
JOIN Producto pr ON d.idProducto = pr.idProducto
WHERE p.idPedido = @idPedido
ORDER BY pr.nombre;


--SELECT POR CORTE 1
SELECT 
    idPedido,
    total,
    SUM(total) OVER () AS TotalGeneral
FROM Pedido
WHERE fecha BETWEEN
      CAST(CONVERT(date, GETDATE()) AS datetime) + '8:00:00'
      AND
      CAST(CONVERT(date, GETDATE()) AS datetime) + '15:00:00'
ORDER BY idPedido;


--SELECTS POR CORTE 2
SELECT 
    idPedido,
    total,
    SUM(total) OVER () AS TotalGeneral
FROM Pedido
WHERE fecha BETWEEN
      CAST(CONVERT(date, GETDATE()) AS datetime) + '15:00:00'
      AND
      CAST(CONVERT(date, GETDATE()) AS datetime) + '23:00:00'
ORDER BY idPedido;

--Consulta que se utilizará para tickets después de pagar
SELECT 
    p.idPedido,
    p.fecha,
    e.nombre AS empleado,
    pr.nombre AS producto,
    d.cantidad,
    pr.precio,
    d.subtotal,
    p.total AS totalPedido
FROM Pedido p
JOIN Empleado e ON p.idEmpleado = e.idEmpleado
JOIN DetallePedido d ON p.idPedido = d.idPedido
JOIN Producto pr ON d.idProducto = pr.idProducto
WHERE p.idPedido = @idPedido
ORDER BY d.idDetalle;

--Reimprimir ticket de alguna orden
SELECT 
    p.idPedido,
    p.fecha,
    e.nombre AS empleado,
    pr.nombre AS producto,
    d.cantidad,
    pr.precio,
    d.subtotal,
    p.total AS totalPedido
FROM Pedido p
JOIN Empleado e 
    ON p.idEmpleado = e.idEmpleado
JOIN DetallePedido d 
    ON p.idPedido = d.idPedido
JOIN Producto pr 
    ON d.idProducto = pr.idProducto
WHERE p.idPedido = @idPedido
ORDER BY d.idDetalle;
