package za.co.discovery.assignment.dinganemosipa.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import za.co.discovery.assignment.dinganemosipa.model.Node;
import za.co.discovery.assignment.dinganemosipa.model.NodeLink;
import za.co.discovery.assignment.dinganemosipa.repository.NodeLinkRepository;
import za.co.discovery.assignment.dinganemosipa.repository.NodeRepository;

@Component
public class DataLoaderImpl implements ApplicationRunner {

	@Autowired
	NodeRepository nodeRepo;

	@Autowired
	private RouteServiceHelper routeServiceHelper;

	@Autowired
	NodeLinkRepository nodeLinkRepo;

	@Value(value = "${xlsx.data.file.location}")
	private String XLSX_DATA_FILE;

	public void readXlsDataFile() {
		try {
			Workbook workbook = createWorkBook();
			processPlanetSheet(workbook);
			processRoutesSheet(workbook);
			routeServiceHelper.initRouteCalculation(nodeRepo.findAll(),nodeLinkRepo.findAll());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	

	private void processPlanetSheet(Workbook workbook) throws FileNotFoundException, IOException {
		Sheet planetsSheet = getSheet(workbook, 0);
		Iterator<Row> iterator = planetsSheet.iterator();
		while (iterator.hasNext()) {
			Row currentRow = iterator.next();
			Cell planetIdCell = currentRow.getCell(0);
			Cell planetNameCell = currentRow.getCell(1);
			if ((planetIdCell.getCellTypeEnum() == CellType.STRING)
					&& (planetNameCell.getCellTypeEnum() == CellType.STRING)) {
				savePlanets(planetIdCell.getStringCellValue(), planetNameCell.getStringCellValue());
			}
		}
	}

		private void processRoutesSheet(Workbook workbook) throws FileNotFoundException, IOException {
		Sheet planetSheet = getSheet(workbook, 1);
		Iterator<Row> it = planetSheet.iterator();
		while (it.hasNext()) {
			Row row = it.next();
			Cell idCell = row.getCell(0);
			Cell sourceCell = row.getCell(1);
			Cell destCell = row.getCell(2);
			Cell weightCell = row.getCell(3);
			short routeID = 0;
			String source = " ";
			String destination = " ";
			BigDecimal distance = BigDecimal.ZERO;
			if (idCell.getCellTypeEnum() == CellType.NUMERIC) {
				routeID = (short) idCell.getNumericCellValue();
			} else {
				continue;
			}
			if (sourceCell.getCellTypeEnum() == CellType.STRING) {
				source = sourceCell.getStringCellValue();
			}
			if (destCell.getCellTypeEnum() == CellType.STRING) {
				destination = destCell.getStringCellValue();
			}
			if (weightCell.getCellTypeEnum() == CellType.NUMERIC) {
				distance = BigDecimal.valueOf(weightCell.getNumericCellValue());
			}
			saveRoute(routeID, source, destination, distance);
		}
	}

	private void saveRoute(short routeID, String source, String destination, BigDecimal distance) {
			if (!Objects.equals(source, destination)) {
				persistRoute(routeID, source, destination, distance);
			}
	}
	
		private Workbook createWorkBook() throws FileNotFoundException, IOException {
		FileInputStream xlsFile = new FileInputStream(new File(XLSX_DATA_FILE));
		return new XSSFWorkbook(xlsFile);
	}
	
		private Sheet getSheet(Workbook workBook, int index) throws FileNotFoundException, IOException {
		return workBook.getSheetAt(index);
	}
	
		private void savePlanets(String nodeSymbol, String desc) {
		if (!nodeSymbol.contains("Node")) {
			Node nodeToSave = new Node();
			nodeToSave.setDescription(desc);
			nodeToSave.setSymbol(nodeSymbol);
			nodeRepo.save(nodeToSave);
		}
	}

	
	/**
	 * Saves routes into the derby DB
	 * @param routeId route ID
	 * @param origin source planet
	 * @param planetDest destination planet
	 * @param distance distance
	 */
	private void persistRoute(Short routeId, String origin, String planetDest, BigDecimal distance) {
		Node source = nodeRepo.findBySymbol(origin);
		Node dest = nodeRepo.findBySymbol(planetDest);
		if ((source != null) && (dest != null)) {
			NodeLink nodeLink=new NodeLink();
			nodeLink.setDistance(distance);
			nodeLink.setSource(source);
			nodeLink.setTarget(dest);
			nodeLinkRepo.save(nodeLink);
		}
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		readXlsDataFile();
	}
}
