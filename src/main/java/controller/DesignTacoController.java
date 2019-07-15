package controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Entity.Taco;
import dao.Ingredient;
import dao.Ingredient.Type;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors) {
		// Save the taco design...
		// We'll do this in chapter 3
		if (errors.hasErrors()) {
			return "design";
		}
		log.info("Processing design: " + design);
		return "redirect:/orders/current";
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = Arrays.asList(
		new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
		new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
		new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
		new Ingredient("CARN", "Carnitas", Type.PROTEIN),
		new Ingredient("CHED", "Cheddar", Type.CHEESE),
		new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
		new Ingredient("SLSA", "Salsa", Type.SAUCE),
		new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
		);
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
			filterByType(ingredients, type));
		}
		model.addAttribute("design", new Taco());
		return "design";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
}