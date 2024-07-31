package cs3220.Controller;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs3220.model.HolidayEntry;

@Controller
public class IndexController {

	public IndexController() {
		entries = new ArrayList<>();
		entries.add(new HolidayEntry("2024-01-01", "New Year's Day"));
		entries.add(new HolidayEntry("2024-01-15", "Martin Luther King Jr. Day"));
		entries.add(new HolidayEntry("2024-02-12", "Lincoln's Birthday"));
		entries.add(new HolidayEntry("2024-02-19", "President's Day"));
		entries.add(new HolidayEntry("2024-07-04", "Independence Day"));
		entries.add(new HolidayEntry("2024-09-02", "Labor Day"));
		entries.add(new HolidayEntry("2024-11-28", "Thanksgiving Day"));
		entries.add(new HolidayEntry("2024-12-25", "Christmas Day"));

	}

	private List<HolidayEntry> entries = new ArrayList<>();

	@GetMapping("/")
	public String index(Model model) {
		entries.sort(Comparator.comparing(HolidayEntry::getDate));
		for (HolidayEntry entry : entries) {
			entry.setFormattedDate(formatDate(entry.getDate()));
		}
		model.addAttribute("entries", entries);
		return "index";
	}

	@GetMapping("/add")
	public String showAddForm() {
		return "AddHoliday";
	}

	@PostMapping("/add")
	public String addHoliday(@RequestParam String day, @RequestParam String month, @RequestParam String year,
			@RequestParam String name, Model model) {

		if (day.isEmpty() || month.isEmpty() || year.isEmpty() || name.isEmpty()) {
			model.addAttribute("errorMessage", "All fields are required");
			return "AddHoliday";
		}

		String date = year + "-" + month + "-" + day;

		for (HolidayEntry entry : entries) {
			if (entry.getDate().equals(date) || entry.getName().equals(name)) {
				model.addAttribute("errorMessage", "Holiday Exists");
				return "AddHoliday";
			}
		}

		entries.add(new HolidayEntry(date, name));
		Collections.sort(entries, Comparator.comparing(HolidayEntry::getDate));
		return "redirect:/";
	}

	@GetMapping("/update")
	public String showUpdateForm(@RequestParam int id, Model model) {
		HolidayEntry entry = getEntryByID(id);
		model.addAttribute("entry", entry);

		String[] dateParts = entry.getDate().split("-");
		model.addAttribute("day", dateParts[2]);
		model.addAttribute("month", dateParts[1]);
		model.addAttribute("year", dateParts[0]);

		return "UpdateHoliday";
	}

	@PostMapping("/update")
	public String updateHoliday(@RequestParam String day, @RequestParam String month, @RequestParam String year,
			@RequestParam String name, @RequestParam int id, Model model) {

		if (name.isEmpty()) {
			model.addAttribute("errorMessage", "All fields are required");
			HolidayEntry entry = getEntryByID(id);
			model.addAttribute("entry", entry);
			model.addAttribute("day", day);
			model.addAttribute("month", month);
			model.addAttribute("year", year);
			return "UpdateHoliday";
		}

		String newDate = year + "-" + month + "-" + day;

		for (HolidayEntry existingEntry : entries) {
			if (existingEntry.getId() != id && existingEntry.getDate().equals(newDate) || existingEntry.getName().equals(name)) {
				model.addAttribute("errorMessage", "Holiday Exists");
				HolidayEntry entry = getEntryByID(id);
				model.addAttribute("entry", entry);
				model.addAttribute("day", day);
				model.addAttribute("month", month);
				model.addAttribute("year", year);
				return "UpdateHoliday";
			}
		}

		HolidayEntry entry = getEntryByID(id);
		entry.setDate(newDate);
		entry.setName(name);

		return "redirect:/";
	}

	@GetMapping("/delete")
	public String deleteHoliday(@RequestParam int id) {
		HolidayEntry entry = null;
		for (HolidayEntry temporary : entries) {
			if (temporary.getId() == id) {
				entry = temporary;
				break;
			}
		}

		if (entry != null) {
			entries.remove(entry);
		}
		return "redirect:/";
	}

	private HolidayEntry getEntryByID(int id) {
		for (HolidayEntry entry : entries) {
			if (entry.getId() == id) {
				return entry;
			}
		}
		return null;
	}

	private String formatDate(String date) {
		String yearNum = date.substring(0, 4);
		String monthNum = date.substring(5, 7);
		String dayNum = date.substring(8, 10);

		int year = Integer.parseInt(yearNum);
		int month = Integer.parseInt(monthNum);
		int day = Integer.parseInt(dayNum);

		String monthName = new DateFormatSymbols().getMonths()[month - 1];

		return String.format("%02d %s %d", day, monthName, year);
	}

}