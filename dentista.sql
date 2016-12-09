-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 01-12-2016 a las 04:54:49
-- Versión del servidor: 10.1.19-MariaDB
-- Versión de PHP: 7.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dentista`
--
CREATE DATABASE IF NOT EXISTS `dentista` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `dentista`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citas`
--

CREATE TABLE `citas` (
  `Dentista` varchar(11) COLLATE utf8_bin NOT NULL,
  `Paciente` varchar(11) COLLATE utf8_bin NOT NULL,
  `Precio` int(11) NOT NULL,
  `Fecha` date NOT NULL,
  `Observaciones` varchar(250) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `citas`
--

INSERT INTO `citas` (`Dentista`, `Paciente`, `Precio`, `Fecha`, `Observaciones`) VALUES
('54023415A', '68', 90, '2017-01-02', 'Blanqueamiento'),
('54023415A', '89023415A', 80, '2016-01-04', 'Ortodoncia'),
('7645321E', '68', 90, '2013-12-30', 'Muelas del Juicio');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dentista`
--

CREATE TABLE `dentista` (
  `DNI` varchar(11) COLLATE utf8_bin NOT NULL,
  `Nombre` varchar(250) COLLATE utf8_bin NOT NULL,
  `Apellidos` varchar(250) COLLATE utf8_bin NOT NULL,
  `Nacimiento` date NOT NULL,
  `Seguridad_Social` int(11) NOT NULL,
  `Movil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `dentista`
--

INSERT INTO `dentista` (`DNI`, `Nombre`, `Apellidos`, `Nacimiento`, `Seguridad_Social`, `Movil`) VALUES
('54023415A', 'Pedro', 'García', '1996-07-15', 875671, 636547207),
('7645321E', 'Ana', 'Pardo', '1990-12-31', 876864, 675123467),
('9856432S', 'Juan', 'Fernandez', '2016-01-04', 4567, 4567);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

CREATE TABLE `paciente` (
  `DNI` varchar(11) COLLATE utf8_bin NOT NULL,
  `Nombre` varchar(250) COLLATE utf8_bin NOT NULL,
  `Apellidos` varchar(250) COLLATE utf8_bin NOT NULL,
  `Nacimiento` date NOT NULL,
  `Email` varchar(250) COLLATE utf8_bin NOT NULL,
  `Direccion` varchar(250) COLLATE utf8_bin NOT NULL,
  `Movil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`DNI`, `Nombre`, `Apellidos`, `Nacimiento`, `Email`, `Direccion`, `Movil`) VALUES
('68', 'Juan', 'onin', '2015-12-06', 'Juanon@g.com', 'Calle 1', 654231789),
('89023415A', 'Lucas', 'Garcia', '1968-01-01', 'LG@g.com', 'Calle 456', 632457690);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`Dentista`,`Paciente`,`Fecha`),
  ADD KEY `Paciente` (`Paciente`);

--
-- Indices de la tabla `dentista`
--
ALTER TABLE `dentista`
  ADD PRIMARY KEY (`DNI`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`DNI`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `citas`
--
ALTER TABLE `citas`
  ADD CONSTRAINT `citas_ibfk_1` FOREIGN KEY (`Paciente`) REFERENCES `paciente` (`DNI`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `citas_ibfk_2` FOREIGN KEY (`Dentista`) REFERENCES `dentista` (`DNI`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
