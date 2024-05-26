package io.github.braayy.literalura.LiterAlura;

import io.github.braayy.literalura.LiterAlura.dto.AuthorDTO;
import io.github.braayy.literalura.LiterAlura.dto.BookDTO;
import io.github.braayy.literalura.LiterAlura.model.Author;
import io.github.braayy.literalura.LiterAlura.model.Book;
import io.github.braayy.literalura.LiterAlura.service.AuthorService;
import io.github.braayy.literalura.LiterAlura.service.BookService;
import io.github.braayy.literalura.LiterAlura.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	private final Scanner scanner = new Scanner(System.in);

	private final GutendexService gutendexService;
	private final BookService bookService;
	private final AuthorService authorService;

    public LiterAluraApplication(GutendexService gutendexService, BookService bookService, AuthorService authorService) {
        this.gutendexService = gutendexService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("LiterAlura\n");
		int opcao = -1;

		while (opcao != 7) {
			System.out.println("1 - Buscar livros");
			System.out.println("2 - Listar todos os livros cadastrados");
			System.out.println("3 - Listar todos os autores cadastrados");
			System.out.println("4 - Listar autores vivos em ano");
			System.out.println("5 - Listar livros escritos em um idioma");
			System.out.println("6 - Top 10 livros mais baixados");
			System.out.println("7 - Sair");

			System.out.println("\nDigite a opção desejada: ");
			opcao = scanner.nextInt();

			switch (opcao) {
				case 1 -> {
					buscarLivro();
				}
				case 2 -> {
					listarLivros();
				}
				case 3 -> {
					listarAutores();
				}
				case 4 -> {
					listarAutoresVivos();
				}
				case 5 -> {
					listarLivrosPorIdioma();
				}
				case 6 -> {
					listarTop10();
				}
			}
		}
	}



	private void buscarLivro() {
		System.out.println("Digite o nome do livro: ");
		String title = scanner.nextLine();

		Optional<BookDTO> bookDtoOpt = this.gutendexService.searchBook(title);
		if (bookDtoOpt.isEmpty()) {
			System.out.println("Não foi encontrado nenhum livro com esse nome!");
			return;
		}

		BookDTO bookDto = bookDtoOpt.get();
		AuthorDTO authorDto = bookDto.authors().get(0);

		Optional<Author> authorOpt = this.authorService.getByName(authorDto.name());
		Author author = authorOpt.orElseGet(() -> {
			return this.authorService.save(new Author(authorDto));
		});

		Optional<Book> bookOpt = this.bookService.getLivro(author, bookDto.title());
		Book book = bookOpt.orElseGet(() -> {
			Book tempBook = new Book(bookDto);
			tempBook.setAuthor(author);

			return this.bookService.save(tempBook);
		});

		System.out.println(book);
	}

	private void listarLivros() {
		this.bookService.getAll().forEach(System.out::println);
	}

	private void listarAutores() {
		this.authorService.getAll().forEach(System.out::println);
	}

	private void listarAutoresVivos() {
		System.out.println("Digite o ano: ");
		int ano = scanner.nextInt();

		this.authorService.getAllAliveIn(ano).forEach(System.out::println);
	}

	private void listarLivrosPorIdioma() {
		System.out.println("Digite o idioma: ");
		scanner.nextLine();
		String idioma = scanner.nextLine();

		this.bookService.getAllByLanguage(idioma).forEach(System.out::println);
	}

	private void listarTop10() {
		this.bookService.getTop10().forEach(System.out::println);
	}
}
