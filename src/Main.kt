val somenteLetrasRegex = Regex("^[A-Za-zÀ-ú ]+\$")
val somenteNumerosRegex = Regex("^\\d+\$")

data class Convidado(
    var nome: String = "",
    var presente: String = "",
    var alimentar: String = "",
    var presenca: Boolean = false
)

val listaConvidados = mutableListOf<Convidado>()

fun main() {
    menu()
}

fun menu() {
    while (true) {
        println("\n--- MENU ---")
        println("1 - Cadastrar")
        println("2 - Editar presença")
        println("3 - Excluir convidado")
        println("4 - Buscar por nome")
        println("0 - Sair")
        print("Escolha uma opção: ")
        when (readln()) {
            "1" -> cadastrar()
            "2" -> editar()
            "3" -> excluir()
            "4" -> busca()
            "0" -> {
                println("Saindo...")
                break
            }
            else -> println("Opção inválida!")
        }
    }
}

fun cadastrar() {
    val convidado = Convidado()

    // QUESTÃO 1 - Validar nome apenas com letras
    while (true) {
        print("Digite o nome do convidado: ")
        val nome = readln()
        if (somenteLetrasRegex.matches(nome)) {
            convidado.nome = nome
            break
        } else {
            println("Nome inválido! Digite apenas letras.")
        }
    }

    print("Digite o presente que irá levar: ")
    convidado.presente = readln()

    print("Digite a restrição alimentar (se houver): ")
    convidado.alimentar = readln()

    listaConvidados.add(convidado)
    println("Convidado cadastrado com sucesso!")
}

fun editar() {
    if (listaConvidados.isEmpty()) {
        println("Nenhum convidado cadastrado.")
        return
    }

    listar()

    // QUESTÃO 2 - Validar posição numérica
    val posicao = lerPosicaoValida("Digite a posição do convidado a editar: ") ?: return

    // QUESTÃO 2 - Validar resposta S/N
    while (true) {
        print("O convidado vai comparecer? (S/N): ")
        val resposta = readln().uppercase()
        if (resposta == "S" || resposta == "N") {
            listaConvidados[posicao].presenca = resposta == "S"
            println("Presença atualizada!")
            break
        } else {
            println("Resposta inválida! Digite apenas S ou N.")
        }
    }
}

fun excluir() {
    if (listaConvidados.isEmpty()) {
        println("Nenhum convidado cadastrado.")
        return
    }

    listar()

    // QUESTÃO 3 - Validar posição numérica
    val posicao = lerPosicaoValida("Digite a posição do convidado a excluir: ") ?: return

    listaConvidados.removeAt(posicao)
    println("Convidado removido com sucesso!")
}

fun busca() {
    // QUESTÃO 4 - Validar nome com apenas letras
    print("Digite o nome a ser buscado: ")
    val nomeBusca = readln()

    if (!somenteLetrasRegex.matches(nomeBusca)) {
        println("Digite apenas letras para a busca!")
        return
    }

    val encontrados = listaConvidados.filter { it.nome.contains(nomeBusca, ignoreCase = true) }

    if (encontrados.isEmpty()) {
        println("Nenhum convidado encontrado com esse nome.")
    } else {
        println("Convidados encontrados:")
        encontrados.forEachIndexed { index, convidado ->
            println("$index - ${convidado.nome}")
        }
    }
}

fun listar() {
    println("Lista de Convidados:")
    listaConvidados.forEachIndexed { index, c ->
        println("$index - Nome: ${c.nome}, Presente: ${c.presente}, Restrição: ${c.alimentar}, Presença: ${if (c.presenca) "Sim" else "Não"}")
    }
}

fun lerPosicaoValida(mensagem: String): Int? {
    while (true) {
        print(mensagem)
        val entrada = readln()
        if (somenteNumerosRegex.matches(entrada)) {
            val numero = entrada.toInt()
            if (numero in listaConvidados.indices) return numero
        }
        println("Posição inválida!")
        return null
    }
}
