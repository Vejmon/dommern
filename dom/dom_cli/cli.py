import typer

from .deploy import gh

app = typer.Typer(help="Dommern CLI", no_args_is_help=True)
app.add_typer(gh.app, name="gh")

def main():
    app()

if __name__ == "__main__":
    main()